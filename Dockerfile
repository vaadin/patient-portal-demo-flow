FROM tomcat:8-jre8
ARG datasourceUrl=localhost:3306
ENV spring.datasource.url=jdbc:mysql://$datasourceUrl/pp
EXPOSE 8080
COPY target/patient-portal-flow-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/portal.war
