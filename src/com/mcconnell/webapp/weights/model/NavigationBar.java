package com.mcconnell.webapp.weights.model;

import java.util.ArrayList;
import java.util.List;

import com.mcconnell.utils.Utils;

public class NavigationBar {
	private String firstPage;
	private String lastPage;
	private List<Map<String,String>> mappings;

	public NavigationBar(String url) {
		String[] pages = url.split("/");
		this.mappings = new ArrayList<Map<String,String>>(pages.length);
		this.firstPage = Utils.capitalize(pages[0]);
		if (pages.length > 0) {
			this.lastPage = Utils.capitalize(pages[pages.length - 1]);
		} else {
			this.lastPage = Utils.capitalize(firstPage);
		}
		String base = "";
		for (int x = 0; x < pages.length; x++) {
			base += "/" + pages[x];
			mappings.add(x, new Map<String,String>(base,Utils.capitalize(pages[x])));
		}
	}

	public String getFirst() {
		return firstPage;
	}

	public String getLast() {
		return lastPage;
	}

	public List<Map<String,String>> getMappings() {
		return mappings;
	}

}
