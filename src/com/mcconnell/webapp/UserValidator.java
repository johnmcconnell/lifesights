package com.mcconnell.webapp;

import java.util.List;

public interface UserValidator<T extends DataObject, U extends UserObject> {
	public boolean isObjectValid(T object, U session);
	public boolean filterObjects(List<T> objects, U session);
}
