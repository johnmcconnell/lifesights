package com.mcconnell.webapp.goaltracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mcconnell.webapp.DataController;
import com.mcconnell.webapp.UserController;
import com.mcconnell.webapp.UserObject;

@Controller
@RequestMapping(AppUserController.BASE_URL)
public class AppUserController extends UserController<UserObject> {
	public static final Class<UserObject> CLASS = UserObject.class;
	public static final String BASE_URL = "/goaltracker/users";
	public static final String COOKIE_KEY = "user";

	public AppUserController() {
		super(CLASS, COOKIE_KEY, 900);
	}

}
