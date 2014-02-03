package com.lifesights.webapp;

import java.util.List;

import javax.jdo.PersistenceManager;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

public abstract class DataController<T extends DataObject> {
	public static final String CREATE_URL = "create";
	public static final String CREATEL_URL = "createAll";
	public static final String READ_URL = "read";
	public static final String READL_URL = "readAll";
	public static final String UPDATE_URL = "update";
	public static final String UPDATEL_URL = "updateAll";
	public static final String DELETE_URL = "delete";
	public static final String DELETEL_URL = "deleteAll";
	public static final String GRANT_URL = "grant";
	public static final String GRANTL_URL = "grantAll";
	private final Class<T> CLASS;

	public DataController(final Class<T> _class) {
		this.CLASS = _class;
	}

	@RequestMapping(value = CREATE_URL, method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse createObject(@RequestBody final T data) {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		TransactionResponse response = data.addToDatabase(pm);
		pm.close();
		return response;
	}

	@RequestMapping(value = UPDATE_URL + "/{key}", method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse updateObject(@PathVariable final String key,
			@RequestBody final T from) {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		final T object = DataObject.getObject(pm, CLASS, key);
		object.update(from);
		pm.close();
		TransactionResponse response = new TransactionResponse(object);
		return response;
	}

	@RequestMapping(value = READ_URL + "/{key}", method = RequestMethod.GET)
	public @ResponseBody
	TransactionResponse readObject(@PathVariable String key) {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		T object = DataObject.getDetachedObject(pm, CLASS, key);
		pm.close();
		TransactionResponse response = new TransactionResponse(object);
		return response;

	}

	@RequestMapping(value = READL_URL, method = RequestMethod.GET)
	public @ResponseBody
	TransactionResponse readObjects() {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		List<T> objects = DataObject.getDetachedObjects(pm, CLASS);
		pm.close();
		TransactionResponse response = new TransactionResponse(objects);
		return response;
	}

	@RequestMapping(value = DELETE_URL + "/{key}", method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse deleteObject(@PathVariable String key) {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		final T object = DataObject.getObject(pm, CLASS, key);
		TransactionResponse response = object.deleteFromDatabase(pm);
		pm.close();
		return response;
	}

	@Deprecated
	@RequestMapping(value = DELETEL_URL, method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse deleteObjects(@RequestBody final List<T> objects,
			@RequestBody UserObject user) {

		final PersistenceManager pm = PMF.get().getPersistenceManager();
		final TransactionResponse validation = validateRequest(pm, user,
				RbacRule.DELETE);
		if (validation == null) {
			TransactionResponse response = DataObject.deleteAllFromDatabase(pm,
					objects);
			pm.close();
			return response;
		} else {
			pm.close();
			return validation;
		}
	}

	@Deprecated
	@RequestMapping(value = GRANT_URL + "/{key}", method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse giveUserOperationOnObject(@PathVariable String key,
			@RequestBody TransactionRequest req) {
		final T object = (T) req.getData();
		final UserObject user = req.getUser();
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		final TransactionResponse validation = validateRequest(pm, user,
				RbacRule.GRANT);
		if (validation == null) {
			// TODO: finish
			pm.close();
			return null;
		} else {
			pm.close();
			return validation;
		}
	}

	@Deprecated
	@RequestMapping(value = CREATEL_URL, method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse createObjects(@RequestBody final List<T> objects,
			@RequestBody final UserObject user) {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		final TransactionResponse validation = validateRequest(pm, user,
				RbacRule.CREATE);
		if (validation == null) {
			TransactionResponse response = DataObject.addAllToDatabase(pm,
					objects);
			addUserToResponse(user, pm, response);
			pm.close();
			return response;
		} else {
			pm.close();
			return validation;
		}
	}

	@Deprecated
	private TransactionResponse validateRequest(PersistenceManager pm,
			UserObject user, String crudId) {
		return null;
		// if (user.isValidUser(pm)) {
		// if (RbacRule.hasPermission(pm, CLASS, user, crudId)) {
		// return null;
		// } else {
		// return new TransactionResponse("Unable to authorize user");
		// }
		// } else {
		// return new TransactionResponse("Unable to validate user");
		// }
	}
	
	private void addUserToResponse(final UserObject user,
			final PersistenceManager pm, TransactionResponse response) {
		UserObject savedUser = UserObject.getUserFromUsername(pm,
				user.getUsername());
		savedUser.updateNonce(user);
	}
}
