package com.mcconnell.webapp.goaltracker.model;

import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.appengine.api.datastore.Text;
import com.mcconnell.webapp.DataObject;

@PersistenceCapable(detachable = "true")
public class Journal extends DataObject {
	private static final Logger log = Logger.getLogger(Journal.class.getName());
	@Persistent
	private String title;
	@Persistent
	private String author;
	@Persistent
	private Text markdown;
	@Persistent
	private boolean published;

	public Journal(String title, String author, Text markdown, boolean published) {
		super();
		this.title = title;
		this.author = author;
		this.markdown = markdown;
		this.published = published;
	}


	@Override
	public void update(DataObject from) {
		if (getClass() != from.getClass()) {
			return;
		}
		Journal other = (Journal) from;
		this.setTitle(other.getTitle());
		this.setAuthor(other.getAuthor());
		this.setMarkdown(other.getMarkdown());
		this.setPublished(other.getPublished());
		this.setUpdatedOn(new Date());
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getMarkdown() {
		return markdown.getValue();
	}


	public void setMarkdown(String markdown) {
		this.markdown = new Text(markdown);
	}

	public boolean getPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

}
