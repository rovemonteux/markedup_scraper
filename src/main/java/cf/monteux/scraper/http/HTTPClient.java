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

package cf.monteux.scraper.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HTTPClient {

	public static String request(String endpoint) {
		String response = "";
		try
        {
            URL url = new URL(endpoint);
            System.out.println("Connecting to "+url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
			scanner.useDelimiter("\\Z");
			response = scanner.next();
			scanner.close();
			stream.close();
			connection.disconnect();
        }
        catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
		return response;
	}
	
}
