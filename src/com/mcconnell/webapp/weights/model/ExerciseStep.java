package com.mcconnell.webapp.weights.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.mcconnell.webapp.DataObject;

@PersistenceCapable
public class ExerciseStep extends DataObject {
	@Persistent
	private String Description;

	@Override
	public void update(DataObject from) {
		
	}
}
