FROM openjdk:8-jdk-alpine
ARG datasourceUrl=localhost:3306
ENV spring.datasource.url=jdbc:mysql://$datasourceUrl/pp
#ADD target/patient-portal-flow-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
EXPOSE 8080
RUN rm -fr /usr/local/tomcat/webapps/ROOT
COPY target/patient-portal-flow-0.0.1-SNAPSHOT /usr/local/tomcat/webapps/ROOT
