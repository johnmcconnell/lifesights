package com.mcconnell.webapps.weights.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.mcconnell.webapps.DataObject;

@PersistenceCapable
public class ExerciseStep extends DataObject {
	@Persistent
	private String Description;

	@Override
	public void update(DataObject from) {
		
	}
}
