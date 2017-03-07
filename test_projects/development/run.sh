#!/bin/sh
cwd=$(pwd)
cd ../../
mvn package
cd $cwd 
java -jar ../../target/Markedup_Scraper-0.0.1-SNAPSHOT-jar-with-dependencies.jar scraper_configuration.json 
