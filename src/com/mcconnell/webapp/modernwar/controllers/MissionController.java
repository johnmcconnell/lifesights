package com.mcconnell.webapp.modernwar.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.Query;
import javax.jdo.PersistenceManager;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcconnell.webapp.PMF;
import com.mcconnell.webapp.modernwar.model.Level;
import com.mcconnell.webapp.modernwar.model.Mission;

@Controller
@RequestMapping("/modernwar/missions")
public class MissionController {
	private static final Logger log = Logger.getLogger(MissionController.class
			.getName());
	// DI via Spring
	String message;

	@RequestMapping(method = RequestMethod.GET)
	public String getMission(ModelMap model) {
		// find the information in the database

		model.addAttribute("missions", getMissions());
		return "Missions";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addMission(@Valid Mission mission, BindingResult result) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if (result.hasErrors()) {
			throw new Error("Invalid Mission Parameters");
		}

		Date now = new Date();
		mission.setCreatedOn(now);
		mission.setUpdatedOn(now);
		mission.setLevels(LevelController.newPlaceHolderList(mission
				.getTotalLevels()));

		try {
			pm.makePersistent(mission);
		} finally {
			pm.close();
		}

		// find the information in the database
		return "redirect:/modernwar/missions/" + mission.getUrlKey();
	}

	@RequestMapping(value = "/{missionKey}", method = RequestMethod.POST)
	public String addLevelToMission(@PathVariable String missionKey,
			@Valid Level level, BindingResult result) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		if (result.hasErrors()) {
			log.info(result.toString());
			throw new Error("Invalid Level Parameters");
		}

		Date now = new Date();
		level.setUpdatedOn(now);
		level.setPlaceholder(false);

		Mission m = getMission(missionKey, pm);

		if (level.getIndex() < m.getTotalLevels()) {
			LevelController.addLevelToCollection(level, m.getLevels());
			m.setUpdatedOn(now);
		} else {
			throw new Error("Level index is greater "
					+ "than the missions total amount of levels");
		}

		pm.close();

		return "redirect:/modernwar/missions/" + missionKey;
	}

	@RequestMapping(value = "/{missionKey}", method = RequestMethod.GET)
	public String getMission(@PathVariable String missionKey, ModelMap model) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Mission mission = getMission(missionKey, pm);
		model.addAttribute("mission", mission);
		model.addAttribute("levels", mission.getLevels());
		return "MissionDetail";
	}

	public static Mission getMission(String name) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Mission m = pm.getObjectById(Mission.class, name);
		pm.close();
		return m;
	}

	public static Mission getMission(String name, PersistenceManager pm) {
		return pm.getObjectById(Mission.class, name);
	}

	@SuppressWarnings("unchecked")
	public static List<Mission> getMissions() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Mission.class);
		q.setOrdering("updatedOn desc");
		List<Mission> missions = null;
		try {
			missions = (List<Mission>) q.execute();
		} finally {
			q.closeAll();
		}
		return missions;
	}

	@SuppressWarnings("unchecked")
	public static List<Mission> getMissions(PersistenceManager pm) {
		Query q = pm.newQuery(Mission.class);
		q.setOrdering("creation desc");
		return (List<Mission>) q.execute();
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
