package com.boost.webapp;

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
	public static final String DATA_PATH = "api";
	private final Class<T> CLASS;
	private UserValidator<T,UserObject> validator;

	public DataController(final Class<T> _class) {
		this.CLASS = _class;
	}

	@RequestMapping(value = DATA_PATH, method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse addObject(@RequestBody final T object) {
		return object.addToDatabase();
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

}
