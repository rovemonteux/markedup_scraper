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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ScraperCLI {

	public static final Logger logger;
	
	static {
        logger = Logger.getLogger(ScraperCLI.class.getName());
    }
	
	public static void main(String[] args) {
		if (System.getProperty("java.util.logging.config.file") == null) {
            try {
                LogManager.getLogManager().readConfiguration(ScraperCLI.class.getResourceAsStream("/log/logging.properties"));
            }
            catch (IOException | SecurityException e) {
                final Exception ex = e;
                System.err.println("LogManager configuration failed: " + ex);
                ex.printStackTrace();
            }
            catch (NullPointerException e) {
            	final Exception ex = e;
            	System.err.println("The default logging properties file, /log/logging.properties, is missing");
            	ex.printStackTrace();
            }
        }
		ScraperCLI scraperCLI = new ScraperCLI();
		try {
			scraperCLI.process(args[0]);
		} catch(FileNotFoundException fe) {
			fe.printStackTrace();
			usage();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void process(String configurationFileName) throws FileNotFoundException, UnsupportedEncodingException {
			logger.info("Opening configuration file "+configurationFileName);
			Configuration configuration = new Configuration(configurationFileName);
			logger.info("Started processing for project: "+configuration.getProject());
			Scraper scraper = new Scraper(configuration);
	}
	public static void usage() {
		logger.severe("Usage: ScraperCLI <configuration file name>");
	}

}
