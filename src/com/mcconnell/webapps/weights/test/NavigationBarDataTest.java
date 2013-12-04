package com.mcconnell.webapps.weights.test;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.mcconnell.webapps.weights.model.Map;
import com.mcconnell.webapps.weights.model.NavigationBar;

public class NavigationBarDataTest {

	@Test
	public void test() {
		NavigationBar data = new NavigationBar("my/test/url/1");
		List<Map<String,String>> paths = data.getMappings();
		String first = data.getFirst();
		String last = data.getLast();
		assertTrue(first.equals("My"));
		checkMap(paths.get(1), "/my/test", "Test");
		checkMap(paths.get(2), "/my/test/url", "Url");
		checkMap(paths.get(3), "/my/test/url/1", "1");
		assertTrue(last.equals("1"));
	}

	private void checkMap(Map<String,String> data, String path, String page) {
		assertTrue(data.getX().equals(path));
		assertTrue(data.getY().equals(page));
	}
}
