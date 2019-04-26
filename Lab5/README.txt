The application uses port 8088, to change you can modify the "server.port" value in the application.properties files, located in src/main/resources

the URLs are the same (/crunchify/<service endpoint>) but there is no additional context path (ex. to convert 5 degrees celsius to fahrenheit would be http/localhost:8088/crunchify/ctofservice/5)

the "precision" query parameter will only acept integer values. if a non-integer value is entered, there will be not precision specified, just as if the paraeter was omitted. if the specified precision is higher than what would normaly be returned, it will pad with trailing 0's to the specified precision.

the Dockerfile looks for a jarfile named "crunchify_spring-0.0.1-SNAPSHOT.jar" in the same directory as the Dockerfile a jar file build from the source tree is already included in the Docker folder the Docker file is located in.

to build the project to an executable jar, you can use either "gradle build" or "gradle bootJar". the executable jar will be located in build/libs.