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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import cf.monteux.scraper.http.HTTPClient;
import cf.monteux.scraper.io.FileIO;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

public class Scraper {
	
	public static final Logger logger;
	
	static {
        logger = Logger.getLogger(Scraper.class.getName());
    }
	
	private Configuration configuration = null;
	
	public static String tagPrefix = "{scraper::";
	public static String tagPostfix="}";
	
	private String template = "";
	private String content = "";
	private ArrayList<String> scraperTags = new ArrayList<String>();
	private ArrayList<Element> scraperElements = new ArrayList<Element>();
	
	// syntax: {scraper::tagname}
	// if tag has attributes, use the tag around the markup
	// if tag has no attributes, look for a tag up in the tree with content or attribute, and then use it as map
	// ignore href attributes

	public Scraper(Configuration configuration_) throws FileNotFoundException {
		this.setConfiguration(configuration_);
		populate();
	}
	
	public void populate() throws FileNotFoundException {
		compileTags();
		//this.setContent(HTTPClient.request(this.getConfiguration().getUrls().get(0)));
	}

	public void compileTags() throws FileNotFoundException {
		Source source = new Source(FileIO.read(this.getConfiguration().getFilenames().get(0)));
		source.fullSequentialParse();
		List<Element> elements = source.getAllElements();
		for (Element element: elements) {
			String elementContent = element.getContent().getTextExtractor().toString();
			if (elementContent.contains("{scraper:") && element.getMaxDepthIndicator() < 2) {
				logger.info("Element: "+element.toString());
				this.getScraperElements().add(element);
				String elementTag = elementContent.replace(tagPrefix, "").replace(tagPostfix, "");
				logger.info("Element tag: "+elementTag);
				this.getScraperTags().add(elementTag);
				elementTag = null;
			}
			elementContent = null;
		}
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public ArrayList<String> getScraperTags() {
		return scraperTags;
	}

	public void setScraperTags(ArrayList<String> scraperTags) {
		this.scraperTags = scraperTags;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	public ArrayList<Element> getScraperElements() {
		return scraperElements;
	}

	public void setScraperElements(ArrayList<Element> scraperElements) {
		this.scraperElements = scraperElements;
	}
	
}
