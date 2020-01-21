# Patient Portal with Vaadin 14

This repo contains a Vaadin 14.2 (with Polymer templates) implementation of the https://github.com/vaadin/patient-portal-demo application.

## Requirements
- Java 8+
- Maven

## Building
This repo has a dependency to the `patient-portal-backend:2.0-SNAPSHOT` module which is **not** available
as a binary maven artifact in a remote repository. So there are two ways to build the project:
1. Use the `localrepo` directory as an additional Maven repo. It has a copy of `patient-portal-backend-2.0-SNAPSHOT.jar` that is updated manually, but should be up-to-date `¯\_(ツ)_/¯`. That's the default, no extra actions are needed.
1. Before building this repo, clone the [patient-portal-backend](https://github.com/vaadin/patient-portal-demo-backend) repo and `mvn clean install` it to install that artifact to your local Maven cache.

Once the dependency is available run `mvn package -Pproduction` to build this repo in production mode.

## Running locally
 - `mvn spring-boot:run`

The app would be running with its own in-memory database on http://localhost:8080.

It's also possible to run the app with an external database used by other Patient Portal implementations. See the comments in the `applicaiton.properties` file for details.
