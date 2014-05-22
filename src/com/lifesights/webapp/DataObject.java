package com.lifesights.webapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * @author john
 * 
 */
@PersistenceCapable(detachable = "true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
@JsonIgnoreProperties({ "key", "deleted" })
public abstract class DataObject {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	protected Date createdOn;
	@Persistent
	protected Date updatedOn;
	@Persistent
	protected boolean deleted;
	@NotPersistent
	private String id;

	public DataObject() {
		Date now = new Date();
		this.createdOn = now;
		this.updatedOn = now;
		this.deleted = false;
	}

	protected void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setKey(Key key) {
		this.id = KeyFactory.keyToString(key);
		this.key = key;
	}

	protected Key getKey() {
		if (key == null && id != null) {
			this.key = KeyFactory.stringToKey(id);
		}
		return key;
	}
	
	public void setId(String id) {
		this.key = KeyFactory.stringToKey(id);
		this.id = id;
	}
	
	public String getId() {
		if (id == null && key != null) {
			this.id = KeyFactory.keyToString(key);
		} 
		return this.id;
	}
	
	public abstract void update(DataObject from);

	/**
	 * @param pm
	 *            , the PersistenceManager to look for objects in the database
	 * @param _class
	 *            , the data class to look up in the database
	 * @param key
	 *            , the key of the object to lookup
	 * @return the object that matches the key in the database
	 */
	public static <T extends DataObject> T getObject(
			PersistenceManager pm, Class<T> _class, String key) {
		T o = null;
		try {
			o = pm.getObjectById(_class, key);
		} finally {

		}
		return o;
	}

	/**
	 * @param _class
	 *            , the data class to look up in the database
	 * @param key
	 *            , the key of the object to lookup the method automatically
	 *            detaches the object so that other parts of the application can
	 *            read and write to them. However writing will not persist in
	 *            the database.
	 * @return the object that matches the key in the database
	 */
	public static <T extends DataObject> T getDetachedObject(final PersistenceManager pm, final Class<T> _class,
			final String key) {
		T o, detached = null;
		try {
			o = pm.getObjectById(_class, key);
			detached = pm.detachCopy(o);
		} finally {
	
		}
		return detached;
	}

	/**
	 * @param pm
	 *            , the PersistenceManager to look for objects in the database
	 * @param _class
	 *            , the data class to look up in the database
	 * @return a list of object from the data class in the database ordered by
	 *         the last time it was updated
	 */
	@SuppressWarnings("unchecked")
	public static <T extends DataObject> List<T> getObjects(
			final PersistenceManager pm, final Class<T> _class) {
		final Query q = pm.newQuery(_class);
		q.setOrdering("updatedOn desc");
		List<T> o = new ArrayList<T>(0);
		try {
			o = (List<T>) q.execute();
		} finally {
			q.closeAll();
		}
		return o;
	}

	/**
	 * @param _class
	 *            , the data class to look up in the database this class
	 *            automatically detaches the objects so that other parts of the
	 *            application can read and write to them. However writing will
	 *            not persist in the database.
	 * @return a list of objects from the data class in the database ordered by
	 *         the last time it was updated
	 */
	@SuppressWarnings("unchecked")
	public static <T extends DataObject> List<T> getDetachedObjects(final PersistenceManager pm,
			final Class<T> _class) {
		final Query q = pm.newQuery(_class);
		q.setOrdering("updatedOn desc");
		List<T> o = null;
		List<T> detached = new ArrayList<T>(0);
		try {
			o = (List<T>) q.execute();
			detached = (List<T>) pm.detachCopyAll(o);
		} finally {
			q.closeAll();
		}
		return detached;
	}
	
	public TransactionResponse addToDatabase() {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		final TransactionResponse response = addToDatabase(pm);
		pm.close();
		return response;
	}

	public TransactionResponse addToDatabase(final PersistenceManager pm) {
		try {
			pm.makePersistent(this);
			return new TransactionResponse(this);
		} catch (Exception e) {
			return new TransactionResponse(e.getMessage());
		}
	}

	public static TransactionResponse addAllToDatabase(final PersistenceManager pm,
			List<? extends DataObject> objects) {
		try {
			pm.makePersistentAll(objects);
			pm.close();
			return new TransactionResponse((List<DataObject>)  objects);
		} catch (Exception e) {
			pm.close();
			return new TransactionResponse(e.getMessage());
		}
	}
	
	public TransactionResponse deleteFromDatabase(final PersistenceManager pm) {
		try {
			pm.deletePersistent(this);
			return new TransactionResponse(this);
		} catch (Exception e) {
			return new TransactionResponse(e.getMessage());
		}
	}
	
	public static TransactionResponse deleteAllFromDatabase(final PersistenceManager pm, List<? extends DataObject> objects) {
		try {
			pm.deletePersistentAll(objects);
			return new TransactionResponse((List<DataObject>) objects);
		} catch (Exception e) {
			return new TransactionResponse(e.getMessage());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result
				+ ((updatedOn == null) ? 0 : updatedOn.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "DataObject [createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + ", deleted=" + deleted + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataObject other = (DataObject) obj;
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
			return false;
		if (deleted != other.deleted)
			return false;
		if (updatedOn == null) {
			if (other.updatedOn != null)
				return false;
		} else if (!updatedOn.equals(other.updatedOn))
			return false;
		return true;
	}
}
