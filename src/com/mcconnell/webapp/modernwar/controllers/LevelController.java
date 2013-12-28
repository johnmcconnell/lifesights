package com.mcconnell.webapp.modernwar.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.mcconnell.webapp.modernwar.model.Level;
import com.mcconnell.webapp.modernwar.model.Mission;

@Controller
@RequestMapping("/modernwar/levels")
public class LevelController {
	public static void addLevelToCollection(Level level, List<Level> levels) {
		long actualIndex = level.getIndex() - 1;
		if (levels.size() < actualIndex) {
			int index = indexOfLastPlaceHolderLevel(levels);
			while (index < actualIndex - 1) {
				index++;
				levels.add(Level.placeholderLevel(index + 1));
			}
			levels.add((int) actualIndex, level);
		} else if (levels.size() == actualIndex) {
			levels.add((int) actualIndex, level);
		} else {
			levels.remove((int) actualIndex);
			levels.add((int) actualIndex, level);
		}
	}
	
	public static int indexOfLastPlaceHolderLevel(List<Level> levels) {
		for (int x = 0; x < levels.size(); x++){
			if (!levels.get(x).getPlaceholder()) {
				return x;
			}
		}
		return levels.size() - 1;
	}
	
	public static List<Level> newPlaceHolderList(long size) {
		List<Level> levels = new ArrayList<Level>((int) size);
		for (int x = 0; x < size; x++) {
			levels.add(Level.placeholderLevel(x));
		}
		return levels;
	}
}
