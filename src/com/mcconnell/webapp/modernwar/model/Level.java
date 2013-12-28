package com.mcconnell.webapp.modernwar.model;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class Level {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	private long totalRuns;
	@Persistent
	private long index;
	@Persistent
	private String name;
	@Persistent
	private Date updatedOn;
	@Persistent
	private boolean placeholder;
	@NotPersistent
	private List<Event> events;
	@NotPersistent
	private String urlKey;

	public Level(int index, String name, Date updatedOn, boolean placeholder,
			List<Event> events, long totalRuns) {
		super();
		this.index = index;
		this.name = name;
		this.updatedOn = updatedOn;
		this.placeholder = placeholder;
		this.events = events;
		this.totalRuns = totalRuns;
	}

	private Level() {
		this.placeholder = true;
	}

	public static Level placeholderLevel(long index) {
		Level level = new Level();
		level.setIndex(index);
		return level;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public boolean getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(boolean placeholder) {
		this.placeholder = placeholder;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updated) {
		this.updatedOn = updated;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public long getTotalRuns() {
		return totalRuns;
	}

	public void setTotalRuns(long totalRuns) {
		this.totalRuns = totalRuns;
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
