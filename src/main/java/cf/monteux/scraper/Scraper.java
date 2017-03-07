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

public class Scraper {
	
	private Configuration configuration = null;
	
	// syntax: {scraper::tagname}
	// if tag has attributes, use the tag around the markup
	// if tag has no attributes, look for a tag up in the tree with content or attribute, and then use it as map
	// ignore href attributes
	
	public Scraper(Configuration configuration_) {
		this.setConfiguration(configuration_);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
}
