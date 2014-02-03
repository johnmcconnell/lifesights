package com.lifesights.webapp;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Unique;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.lifesights.utils.PasswordHash;

@PersistenceCapable(detachable = "true")
@JsonIgnoreProperties({ "hash", "timeOfLastAction"})
public class UserObject extends DataObject {
	@Persistent
	@Unique
	private String username;
	@NotPersistent
	private String password;
	@Persistent
	private String hash;
	@Persistent
	private Date timeOfLastAction;
	@Persistent
	private int nonce;
	@Persistent
	private List<String> roles;


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
	
	
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	

	public Date getTimeOfLastAction() {
		return timeOfLastAction;
	}

	public void setTimeOfLastAction(Date timeOfLastAction) {
		this.timeOfLastAction = timeOfLastAction;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public boolean isValidUser(final PersistenceManager pm) {
		final UserObject trueUser = getUserFromUsername(pm, this.getUsername());
		final String goodHash = trueUser.getHash();
		final int goodNonce = trueUser.getNonce();
		
		if (goodHash == null || goodNonce == 0) {
			return false;
		}
		
		boolean valid = (goodNonce == this.getNonce() && PasswordHash.slowEquals(goodHash.getBytes(),this.getHash().getBytes()));
		if (valid) {
			this.updateNonce(trueUser);
		}
		return valid;
	}
	
	public boolean logout(final PersistenceManager pm) {
		final UserObject trueUser = getUserFromUsername(pm, this.getUsername());
		final String goodHash = trueUser.getHash();
		if (goodHash == null) {
			return false;
		}
		boolean valid = PasswordHash.slowEquals(goodHash.getBytes(),this.getHash().getBytes());
		if (valid) {
			this.setNonce(new Random().nextInt());
		}
		return valid;
	}
	
	public void updateNonce(UserObject boundUser) {
		int nounce = new Random().nextInt();
		this.setNonce(nounce);
		boundUser.setNonce(nounce);
	}

	public static UserObject getUserFromUsername(
			PersistenceManager pm, String username) {
		UserObject o = null;
		try {
			Query q = pm.newQuery(UserObject.class);
			q.setFilter("username == name");
			q.declareParameters("String name");
			q.setUnique(true);
			o = (UserObject) q.execute(username);
		} finally {

		}
		return o;
	}	
	
	@Deprecated
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
