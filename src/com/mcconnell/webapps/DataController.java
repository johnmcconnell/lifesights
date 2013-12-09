package com.mcconnell.webapps;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class DataController<T extends DataObject> {
	public static final String DATA_PATH = "api";
	private final String IndexFileName;
	private final Class<T> CLASS;

	public DataController(Class<T> _class, String IndexFileName) {
		this.CLASS = _class;
		this.IndexFileName = IndexFileName;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getIndex() {
		return IndexFileName;
	}

	@RequestMapping(value = DATA_PATH, method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse addObject(@RequestBody final T object) {
		return object.addToDatabase();
	}
	
	@RequestMapping(value = DATA_PATH + "/{key}", method = RequestMethod.PUT)
	public @ResponseBody
	TransactionResponse updateObject(@PathVariable String key, @Valid @RequestBody final T from) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		T object = DataObject.getObject(pm, CLASS, key);
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
