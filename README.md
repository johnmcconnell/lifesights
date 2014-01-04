Okay if you are new to this repo, I will try to break it down quickly.

There are a useful set of http api for adding, updating, and eventually deleting objects in a database.

This library was designed for using ajax and json to send database objects to the client. 

A quick example:

The controller:
`package com.mcconnell.webapp.goaltracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mcconnell.webapp.DataController;

@Controller
@RequestMapping(JournalController.BASE_URL)
public class JournalController extends DataController<Journal> {
	public static final Class<Journal> CLASS = Journal.class;
	public static final String BASE_URL = "/goaltracker/journal";

	public JournalController() {
		super(CLASS);
	}
}`

The model:


> `@PersistenceCapable(detachable = "true")
> public class Journal extends DataObject {
> 	@Persistent
> 	private String title;
> 	@Persistent
> 	private String author;
> 	@Persistent
> 	private String markdown;
> 	@Persistent
> 	private boolean published;
> 	
> 	public Journal(String title, String author, String markdown, boolean published) {
> 		super();
> 		this.title = title;
> 		this.author = author;
> 		this.markdown = markdown;
> 		this.published = published;
> 	}
> 
> 	@Override
> 	public void update(DataObject from) {
> 		if (getClass() != from.getClass()) {
> 			return;
> 		}
> 		Journal other = (Journal) from;
> 		this.setTitle(other.getTitle());
> 		this.setAuthor(other.getAuthor());
> 		this.setMarkdown(other.getMarkdown());
> 		this.setPublished(other.getPublished());
> 		this.setUpdatedOn(new Date());
> 	}
> 
> 	public String getTitle() {
> 		return title;
> 	}
> 
> 	public void setTitle(String title) {
> 		this.title = title;
> 	}
> 
> 	public String getAuthor() {
> 		return author;
> 	}
> 
> 	public void setAuthor(String author) {
> 		this.author = author;
> 	}
> 
> 	public String getMarkdown() {
> 		return markdown;
> 	}
> 
> 	public void setMarkdown(String markdown) {
> 		this.markdown = markdown;
> 	}
> 
> 	public boolean getPublished() {
> 		return published;
> 	}

> 	public void setPublished(boolean published) {
> 		this.published = published;
> 	}
> 
> }`

Tell spring mvc to host the controller
edit the mvc-dispatcher-servlet.xml example:
> <!-- Tell Spring to host controller -->
> <bean class="com.mcconnell.webapp.goaltracker.controllers.JournalController" />

With these changes:
there will be api to http handlers to get, add, update the objects.

