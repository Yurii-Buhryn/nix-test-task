# Start with a base image containing Java runtime
FROM openjdk:10-jdk

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8081 available to the world outside this container
EXPOSE 8081

# The application's jar file
ARG JAR_FILE=target/order-catalog-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} order-catalog.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/order-catalog.jar"]