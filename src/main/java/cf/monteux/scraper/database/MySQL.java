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

package cf.monteux.scraper.database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class MySQL implements Database {

	private Connection connection = null;
	
	public MySQL() throws SQLException, FileNotFoundException {
		String settings = cf.monteux.scraper.io.FileIO.read("settings.cfg");
		String[] setting = settings.split(",");
		this.setConnection(DriverManager.getConnection(setting[0],setting[1],setting[2]));
		setting = null;
		settings = null;
		dropTable();	
		createTable();
	}

	public void dropTable() {
		String table = "DROP TABLE `companies`;";
		execute(table);	
	}

	public void createTable() {
		String table = "CREATE TABLE `companies` (" +
		"`id`  int NULL AUTO_INCREMENT ," +
		"`market`  varchar(10) NULL ," +
		"`company`  varchar(255) NULL ," +
		"`ticker`  varchar(10) NULL ," +
		"`cap`  float NULL ," +
		"`shares`  float NULL ," +
		"`price`  float NULL ," +
		"`changeinprice`  float NULL ," +
		"`changepercentage`  float NULL ," +
		"`dividend`  float NULL ," +
		"`datemodified`  timestamp NULL ," +
		"UNIQUE (`ticker`)," +
		"INDEX (`id`)" +
		");";
		execute(table);	
	}

	public void execute(String query) {
		try {
                        Statement statement = this.getConnection().createStatement();
                        statement.executeQuery(query);
                        statement.close();
                        statement = null;
                } catch (SQLException e) {
                        e.printStackTrace();
                }
	}
	
	public PreparedStatement statement(String statement) throws SQLException {
		return this.getConnection().prepareStatement(statement);
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
