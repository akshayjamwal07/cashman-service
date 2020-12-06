# Introduction

Cashman-service is a spring boot application created to simulate the behaviour of a cash-dispenser(Automated teller machine). 
The application offers to the user the following features -

* Withdraw Cash 
* Add Cash
* Update Denomination
* Get Available Cash
* Get Available Denominations
* Periodic cash inventory scans

## <a href="build-api"></a> Initial state
Cashman is filled with money on application startup with the following denominations -
* 'Ten'     - 50
* 'Twenty'  - 40
* 'Fifty'   - 30

This cash inventory updates according to the user actions. On reaching the threshold value, cashman warns to add cash.

# <a href="build-api"></a> Build the API

* Clone this repo into a directory `git clone https://github.com/akshayjamwal07/cashman-service.git`
* Change directory to the newly cloned repo: `cd cashman-service`
* Run the build `./gradlew clean build` (or to build without tests run `./gradlew clean build -x test`)
* The build will compile, verify against static code analysis tools (checkstyle, PMD, SpotBugs), execute tests, and package the application.
* The build will produce an Uber JAR at `build/libs/cashman-service-1.0.0.jar`

# Build Local Docker Image
To build the Docker image locally, run `./gradlew clean build` to build the jar package first.

Then run `docker build . --build-arg APPLICATION_NAME=cashman-service`.

### PMD Report

To run the PMD tool over the main and test code to find potential issues run the following command:
```
./gradlew pmdMain pmdTest
```
and a HTML test report will be written to `./build/reports/pmd/test.html` for test code and
to `./build/reports/pmd/main.html` for main code.

Our code should result in zero PMD issues before commiting the code to master.

### SpotBugs Report

To run the SpotBugs tool over the code to find potential issues run the following command:
```
./gradlew findbugsMain findbugsTest
```
and a HTML test report will be written to `./build/reports/findbugs/test.html` for test code and
to `build/reports/findbugs/main.html` for main code.

Our code should result in zero Findbug issues before commiting the code to master.
NB. The findbugs configuration is yet to be finalized.

### Technologies
- Spring Boot - To launch the application in tomcat.
- Spring Data - Java Persistence to communicate with DISC.
- Jackson Databind
- OPEN-API
- Immutables 
- MapStruct
- Junit
- CheckStyle
- PMD
- SpotBugs

### Related URLs
Cashman-service source code is stored in Github [https://github.com/akshayjamwal07/cashman-service](https://github.com/akshayjamwal07/cashman-service)

# <a href="run-api"></a> Run the API

Once cloned this application can be run as a spring boot application (main method is in the
sc/com/assessment/cashman/CashManApplication.java).  
An example command line to run the Cashman Service after it has been build would be :
```
${JAVA_HOME}/bin/java - jar build/libs/cashman-service-1.0.0.jar
```

# Documentation


 * User Guide: [http://localhost:8080](http://localhost:8080)
 * Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
 * Swagger Docs: [http://localhost:8080/v3/api-docs/](http://localhost:8080/v3/api-docs/)
