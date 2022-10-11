# AMF (Apex Map Finder)

AMF is a project designed to improve the quality of life for Apex Legends players. By utilizing data from the Apex Legends API, AMF provides features not directly included in the game. Its main purpose is to keep players informed on the different map rotations for each game mode. More features are to come so stay tuned!

## Features

- View current and next maps in rotation
- A timer for each gamemode to see when the maps will change
- Player Search
- Notification service which will notifiy users via email when their specified maps are coming up (WIP)

## Setup

To run this project, install it locally:
`git clone https://github.com/StonelnFocus/AMF`

You will also need to stand up a mySQL database, this can be using the docker-compose.yml file or some other way.

Environment Variables:
```
APEX_API_KEY = [Your own Apex Legends API key]*
MYSQL_DB_USERNAME = [Username for local mySQL database]**
MYSQL_DB_PASSWORD = [Password for local mySQL database]**
```
\*See here to generate an API key: https://portal.apexlegendsapi.com/  
\*\*If using docker-compose, set username and password to what is in the file

## Technologies

**Frontend stack:**
- HTML5 
- CSS3
- Bootstrap 5
- Thymeleaf

**Backend stack:**
- Java
- Spring Boot
- MySQL 
- Heroku 

## Project Status

View the current website at: https://apex-map-finder.herokuapp.com  
More features are continually being added, there is no set schedule for updates
