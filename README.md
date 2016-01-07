# expense-tracker
A sample project to demonstrate how a web app can be built using a Spring MVC/ AngularJS stack. 

### Installation dependencies ###
The following dependencies are necessary :
- Java 8
- bower
- maven 3
- Node 

### Installing frontend dependencies ###
After cloning the repository, the following command installs the Javascript dependencies:

		bower install

### Building and starting the server ###

To build the backend and start the server, run the following command on the root folder of the repository:

    mvn clean install tomcat7:run-war -Dspring.profiles.active=test

The spring test profile will activate an in-memory database. After the server starts, the application is accessible at the following URL:

    http://localhost:8080/





