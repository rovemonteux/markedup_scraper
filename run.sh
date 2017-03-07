#!/bin/sh
mvn package
java -jar target/Markedup_Scraper-0.0.1-SNAPSHOT-jar-with-dependencies.jar $1
