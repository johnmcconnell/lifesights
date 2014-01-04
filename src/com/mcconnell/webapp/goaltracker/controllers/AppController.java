package com.mcconnell.webapp.goaltracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(AppController.BASE_URL)
public class AppController {
	public static final String BASE_URL = "/goaltracker";
	@RequestMapping(method = RequestMethod.GET)
	public String getIndex() {
		return "GoalTrackerApp";
	}
}
