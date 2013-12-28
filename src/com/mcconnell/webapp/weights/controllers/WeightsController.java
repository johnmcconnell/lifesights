package com.mcconnell.webapp.weights.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcconnell.webapp.weights.model.NavigationBar;

@Controller
@RequestMapping(WeightsController.URL)
public class WeightsController {
	public static final String URL = "weights";
	
	@RequestMapping(method = RequestMethod.GET)
	public String getExcercises(ModelMap model) {
		model.addAttribute("navbar", new NavigationBar(URL));
		return "WeightsHome";
	}

}
