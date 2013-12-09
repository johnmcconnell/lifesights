package com.mcconnell.webapps.goaltracker.model;

import java.util.Date;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.mcconnell.webapps.DataObject;

@PersistenceCapable(detachable = "true")
public class DailyGoal extends DataObject {
	@Persistent
	String name;
	@Persistent
	String description;

	public DailyGoal(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DailyGoal other = (DailyGoal) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DailyGoal [name=" + name + ", desciption=" + description + "]";
	}

	@Override
	public void update(DataObject from) {
		if (getClass() != from.getClass()) {
			return;
		}
		DailyGoal other = (DailyGoal) from;
		this.setName(other.getName());
		this.setDescription(other.getDescription());
		this.setUpdatedOn(new Date());
	}

}
