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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Text getSomething() {
		return something;
	}
	public void setSomething(Text something) {
		this.something = something;
	}
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
}

