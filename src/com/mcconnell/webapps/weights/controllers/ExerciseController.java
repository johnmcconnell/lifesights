package com.mcconnell.webapps.weights.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcconnell.webapps.weights.model.NavigationBar;


@Controller
@RequestMapping(ExerciseController.URL)
public class ExerciseController {
	public static final String URL = "weights/exercises";
	
	@RequestMapping(method = RequestMethod.GET)
	public String getExcercises(ModelMap model) {
		
		model.addAttribute("navbar", new NavigationBar(URL));
		return "ExercisesList";
	}
	
	@RequestMapping(value="/{key}", method = RequestMethod.GET)
	public String getExercise(@PathVariable String key, ModelMap model) {
		return null;
	}

}
