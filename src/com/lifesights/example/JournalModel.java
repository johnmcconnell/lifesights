package com.lifesights.example;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Text;
import com.lifesights.webapp.DataObject;

@PersistenceCapable(detachable = "true")
public class JournalModel extends DataObject {
	@Persistent
	private String author;
	@Persistent
	private String title;
	@Persistent
	private Text something;
	@Persistent
	private boolean published;
	@Persistent
	private List<String> tags;
	@Override
	public void update(DataObject from) {
		if (getClass() != from.getClass()) {
			return;
		}
		JournalModel other = (JournalModel) from;
		this.author = other.author;
		this.title = other.title;
		this.something = other.something;
		this.published = other.published;
		this.tags = other.tags;
	}
}

