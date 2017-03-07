/*
 	Markedup Scraper - Java package for helping scraping data from websites and webservices.
 	Copyright (c) 2017 Rove Monteux
 
 	This file is part of Markedup Scraper.
 	
 	This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

package cf.monteux.scraper;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cf.monteux.scraper.io.FileIO;

public class Configuration {
	
	private String configurationName = "";
	private String project = "";
	private String description = "";
	private String userAgent = "";
	private ArrayList<String> filenames = new ArrayList<String>();
	private ArrayList<String> urls = new ArrayList<String>();
	private ArrayList<Integer> throttlings = new ArrayList<Integer>();
	private ArrayList<String> types = new ArrayList<String>();
	private ArrayList<String> names = new ArrayList<String>();
	
	public Configuration(String configurationName_) throws UnsupportedEncodingException, FileNotFoundException {
		this.setConfigurationName(configurationName_);
		populate();
	}
	
	public void populate() throws UnsupportedEncodingException, FileNotFoundException {
		JSONObject jsonObject = new JSONObject(FileIO.read(this.getConfigurationName()));
		JSONObject configuration = jsonObject.getJSONObject("configuration");
		this.setProject(configuration.getString("project"));
		this.setDescription(configuration.getString("description"));
		JSONArray template = configuration.getJSONArray("template");
		for (int a=0; a<template.length();a++) {
			String filename = template.getJSONObject(a).getString("filename");
			this.getFilenames().add(filename);
			String url = template.getJSONObject(a).getString("url");
			this.getUrls().add(url);
			int throttling = template.getJSONObject(a).getInt("throttling");
			this.getThrottlings().add(throttling);
		}
		JSONArray destination = configuration.getJSONArray("destination");
		for (int a=0; a<destination.length();a++) {
			String type = destination.getJSONObject(a).getString("type");
			this.getTypes().add(type);
			String name = destination.getJSONObject(a).getString("name");
			this.getNames().add(name);
		}
		this.setUserAgent(configuration.getString("useragent"));
	}
	
	public ArrayList<String> getFilenames() {
		return filenames;
	}
	public void setFilenames(ArrayList<String> filenames) {
		this.filenames = filenames;
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public ArrayList<String> getUrls() {
		return urls;
	}

	public void setUrls(ArrayList<String> urls) {
		this.urls = urls;
	}

	public ArrayList<String> getTypes() {
		return types;
	}

	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}

	public ArrayList<String> getNames() {
		return names;
	}

	public void setNames(ArrayList<String> names) {
		this.names = names;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Integer> getThrottlings() {
		return throttlings;
	}

	public void setThrottlings(ArrayList<Integer> throttlings) {
		this.throttlings = throttlings;
	}
	
}
