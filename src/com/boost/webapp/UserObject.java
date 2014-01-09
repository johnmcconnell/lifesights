package com.boost.webapp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Unique;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@PersistenceCapable(detachable = "true")
@JsonIgnoreProperties({ "hash" })
public class UserObject extends DataObject {
	@Persistent
	@Unique
	String username;
	@NotPersistent
	String password;
	@Persistent
	String hash;


	@Override
	public String toString() {
		return "UserObject [username=" + username + ", password=" + password
				+ ", hash=" + hash + "]";
	}

	public UserObject(String username, String password, String hash) {
		super();
		this.username = username;
		this.password = password;
		this.hash = hash;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@SuppressWarnings("unchecked")
	public static <T extends UserObject> T getUserFromUsername(
			PersistenceManager pm, Class<T> _class, String username) {
		T o = null;
		try {
			Query q = pm.newQuery(_class);
			q.setFilter("username == name");
			q.declareParameters("String name");
			q.setUnique(true);
			o = (T) q.execute(username);
		} finally {

		}
		return o;
	}

	@Override
	public void update(DataObject from) {
		// TODO Auto-generated method stub

	}
}
