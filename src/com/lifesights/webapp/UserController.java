package com.lifesights.webapp;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.jdo.PersistenceManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifesights.utils.PasswordHash;

@Controller
@RequestMapping("api/model/user")
public class UserController extends DataController<UserObject> {

	public UserController() {
		super(UserObject.class);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse login(@RequestBody final UserObject user) throws NoSuchAlgorithmException, InvalidKeySpecException {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		final String hash = PasswordHash.createHash(user.getPassword());
		user.setHash(hash);
		if (user.isValidUser(pm)) {
			pm.close();
			return new TransactionResponse(user);
		}
		return new TransactionResponse("Unable to login user");
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse logout(@RequestBody final UserObject user) {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		if (user.logout(pm)) {
			return new TransactionResponse("there was no error!");
		}
		return new TransactionResponse("there was an error!");
	}
}
