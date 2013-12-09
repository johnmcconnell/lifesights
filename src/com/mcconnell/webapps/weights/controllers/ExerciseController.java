package com.mcconnell.webapps.weights.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcconnell.webapps.DataObject;
import com.mcconnell.webapps.TransactionResponse;
import com.mcconnell.webapps.weights.model.Exercise;

@Controller
@RequestMapping(ExerciseController.URL)
public class ExerciseController {
	public static final String URL = "weights/exercises";
	public static final Class<Exercise> CLASS = Exercise.class;
	public static final String DATA_PATH = "data";

	@RequestMapping(method = RequestMethod.GET)
	public String getIndex(ModelMap model) {
		return "ExerciseHome";
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse addExercise(@Valid @RequestBody final Exercise exercise) {
		// Perform Database Transaction
		return exercise.addToDatabase();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	TransactionResponse addExercises(@Valid @RequestBody final List<Exercise> exercises) {
		// Perform Database Transaction
		return DataObject.addAllToDatabase(exercises);
	}

	@RequestMapping(value = DATA_PATH + "/all", method = RequestMethod.GET)
	public @ResponseBody
	List<Exercise> getExercises() {
		List<Exercise> exercises = DataObject.getDetachedObjects(Exercise.class);
		return exercises;
	}

	@RequestMapping(value = DATA_PATH + "/{key}", method = RequestMethod.GET)
	public @ResponseBody
	Exercise getExercise(@PathVariable String key) {
		Exercise e = DataObject.getDetachedObject(CLASS, key);
		return e;
	}

}
