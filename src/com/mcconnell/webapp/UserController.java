package com.mcconnell.webapp;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcconnell.utils.Utils;
import com.mcconnell.utils.PasswordHash;

public abstract class UserController<T extends UserObject> {
	private static final Logger log = Logger.getLogger(UserController.class
			.getName());
	private static final String NOT_VALID = "invalid username or password!";
	public static final String DATA_PATH = "api";
	private final Class<T> CLASS;
	private final int COOKIE_EXPIRE_SECONDS;
	private final String COOKIE_KEY;

	public UserController(final Class<T> _class, final String key,
			final int expiry) {
		this.CLASS = _class;
		this.COOKIE_KEY = key;
		this.COOKIE_EXPIRE_SECONDS = expiry;

	}

	private void addUserCookies(HttpServletResponse response, T user) {
		addSessionCookie(response, "user", Utils.objectToJson(user),
				COOKIE_EXPIRE_SECONDS);
	}

	private static void addSessionCookie(HttpServletResponse response, String key,
			String value, int expire) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(expire);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	@RequestMapping(value = DATA_PATH, method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse addObject(@RequestBody final UserObject object) {
		try {
			object.setHash(PasswordHash.createHash(object.getPassword()));
			return object.addToDatabase();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			return new TransactionResponse(
					"Could not save user because there was a problem creating the hash!");
		}
	}

	@RequestMapping(value = DATA_PATH + "/load", method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse load(String username, String password,
			HttpServletResponse response) {
		if (password == null) {
			return new TransactionResponse(
					"could not store session, no password was sent!");
		}
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		final T user = UserObject.getUserFromUsername(pm, CLASS, username);
		if (user == null) {
			return new TransactionResponse("could not store session, no user by that name!");
		}
		try {
			if (PasswordHash.validatePassword(password, user.getHash())) {
				// add cookie to store user
				addUserCookies(response, user);
				pm.close();
				return new TransactionResponse();
			} else {
				pm.close();
				return new TransactionResponse(NOT_VALID);
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			return new TransactionResponse(e.getMessage());
		}
	}

	@RequestMapping(value = DATA_PATH + "/unload", method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse unload(HttpServletResponse response) {
		// effectively removes the cookie
		addSessionCookie(response, COOKIE_KEY, "", 0);
		return new TransactionResponse();
	}

	@RequestMapping(value = DATA_PATH + "/all", method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse addObjects(@RequestBody final List<T> objects) {
		return DataObject.addAllToDatabase(objects);
	}

	@RequestMapping(value = DATA_PATH + "/all", method = RequestMethod.GET)
	public @ResponseBody
	List<T> getObjects() {
		return DataObject.getDetachedObjects(CLASS);
	}

	@RequestMapping(value = DATA_PATH + "/{key}", method = RequestMethod.GET)
	public @ResponseBody
	T getObject(@PathVariable String key) {
		return DataObject.getDetachedObject(CLASS, key);
	}

	@RequestMapping(value = DATA_PATH + "/{key}", method = RequestMethod.PUT)
	public @ResponseBody
	TransactionResponse updateObject(@PathVariable final String key,
			@RequestBody final T from) {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		final T object = DataObject.getObject(pm, CLASS, key);
		object.update(from);
		pm.close();
		return new TransactionResponse();
	}

}
