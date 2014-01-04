package com.mcconnell.webapp.goaltracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mcconnell.webapp.DataController;
import com.mcconnell.webapp.goaltracker.model.Journal;

@Controller
@RequestMapping(JournalController.BASE_URL)
public class JournalController extends DataController<Journal> {
	public static final Class<Journal> CLASS = Journal.class;
	public static final String BASE_URL = "/goaltracker/journal";

	public JournalController() {
		super(CLASS);
	}
}
