package com.mcconnell.webapps.weights.model;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class Exercise extends DataObject {
	@Persistent
	String name;
	@Persistent
	List<ExerciseStep> steps;
}
