# Progress Tracker

Simple application designed for tracking attendee progress through a workshop. 

## Use it Yourself!

This a standard pixel tracking type application. For instructions on; deploying, using, and viewing data collected by this application read below. 

### Deployment Requirements

* JDK 16 (or Docker)
* Oracle 19c

### Deployment

Steps on how to deploy the application....

JVM args:

```
--spring.datasource.username=[db-username] 
--spring.datasource.password=[db-password]
--spring.datasource.url=jdbc:oracle:thin:@[connection_name]?TNS_ADMIN=[walletLocation]
```


#### Database Setup and Configuration

The application uses basic JPA CRUD Spring Data functionality and has been deployed and tested against an Oracle 19c database. Other databases should be compatible given the very basic database functionality requirements, but have not been validated against. 

The application uses two database tables:

`PROGRESS_DATA`

User progress and tracking data is stored in this data. This table is composed of the following columns:

* `id`, NUMERIC : Standard automatically numeric id
* `labId`, CHAR: Id of the lab the user is attending, (see)[#client_side] for details on value stored here.
* `stepId`, CHAR: Current step the user is on, (see)[#client_side] for details on value stored here.
* `trackingId`, CHAR: Id for correlating request from a user. This is automatically generated by the application as a JSessionID. 
* `jsonData`, BLOB: All header values stored as a JSON object for additional information about user 

### Client Side

The tracking endpoint resides at `GET:/p/{labId}/{stepId}` and returns a 1x1 white pixel PNG file. 

* `labId`: Should be human readable lab id. Example: if you have a workshop for deploying Java applications on Kubernetes, a lab id could be: `java-on-k8s`
* `stepId`: The step within the lab the user is on. Recommend this be a numeric value, but is handled as a `String`. 

Application does require cookies to be enabled. A JSessionID is generated and stored as a means of correlating requests from a user during a session. 

### Viewing Tracking Data

For instructions on doing this yourself [see this baeldung guide](https://www.baeldung.com/spring-boot-crud-thymeleaf)

## Questions & Issues

If you have questions about this application or are running into an issue:

Twitter: [@BillyKorando](https://twitter.com/BillyKorando) 

Email: wkorando [at] gmail

## License & Attribution

Copyright (c) 2021 Billy Korando 

Source Code licensed under [MIT License](LICENSE)

Documentation, Slides, and any other creative works licensed under [Creative Commons Attribution-NonCommercial 4.0 International License](LICENSE.md)