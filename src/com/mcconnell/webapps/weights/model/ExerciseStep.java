package com.mcconnell.webapps.weights.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class ExerciseStep extends DataObject {
	@Persistent
	private String Description;
}
