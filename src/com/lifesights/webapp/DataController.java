package com.lifesights.webapp;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;

@SessionAttributes("session")
public abstract class DataController<T extends DataObject> {
	public static final String CREATE_URL = "create";
	public static final String CREATEL_URL = "createAll";
	public static final String READ_URL = "read";
	public static final String READL_URL = "readAll";
	public static final String UPDATE_URL = "update";
	public static final String UPDATEL_URL = "updateAll";
	public static final String DELETE = "delete";
	public static final String DELETEL_URL = "deleteAll";
	private final Class<T> CLASS;
	private UserValidator<T,UserObject> validator;

	public DataController(final Class<T> _class) {
		this.CLASS = _class;
	}

	@RequestMapping(value = CREATE_URL, method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse createObject(@RequestBody final T object) {
		return object.addToDatabase();
	}

	@RequestMapping(value = UPDATE_URL + "/{key}", method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse updateObject(@PathVariable final String key,
			@RequestBody final T from) {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		final T object = DataObject.getObject(pm, CLASS, key);
		object.update(from);
		pm.close();
		return new TransactionResponse();
	}

	@RequestMapping(value = CREATEL_URL, method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse createObjects(@RequestBody final List<T> objects) {
		return DataObject.addAllToDatabase(objects);
	}

	@RequestMapping(value = READL_URL, method = RequestMethod.GET)
	public @ResponseBody
	List<T> readObjects() {
		return DataObject.getDetachedObjects(CLASS);
	}

	@RequestMapping(value = READ_URL + "/{key}", method = RequestMethod.GET)
	public @ResponseBody
	T readObject(@PathVariable String key) {
		return DataObject.getDetachedObject(CLASS, key);
	}

}
