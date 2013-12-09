package com.mcconnell.webapps.goaltracker.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.mcconnell.webapps.DataController;
import com.mcconnell.webapps.goaltracker.model.DailyGoal;

@Controller
@RequestMapping(GoalController.BASE_URL)
public class GoalController extends DataController<DailyGoal> {
	public static final Class<DailyGoal> CLASS = DailyGoal.class;
	public static final String BASE_URL = "/goaltracker";

	public GoalController() {
		super(DailyGoal.class, "GoalTrackerApp");
	}
}
