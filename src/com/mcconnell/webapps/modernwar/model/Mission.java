package com.mcconnell.webapps.modernwar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class Mission {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	private String name;
	@Persistent
	private Date createdOn;
	@Persistent
	private Date updatedOn;
	@Persistent
	private long totalLevels;
	@Persistent
	private List<Level> levels = new ArrayList<Level>();
	@NotPersistent
	private String urlKey;

	public Mission(String name, Date updatedOn, Date createdOn, long totalLevels,
			List<Level> levels) {
		this.name = name;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.totalLevels = totalLevels;
		this.levels = levels;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTotalLevels() {
		return totalLevels;
	}

	public void setTotalLevels(long totalLevels) {
		this.totalLevels = totalLevels;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
	public List<Level> getLevels() {
		return levels;
	}

	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}

	public String getUrlKey() {
		if (urlKey != null || key == null) {
			return urlKey;
		} else {
			return KeyFactory.keyToString(key);
		}
	}

	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}

}
