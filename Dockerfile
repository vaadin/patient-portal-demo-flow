FROM tomcat:8-jre8
ARG datasourceUrl=localhost:3306
ARG jpaAuto=none
ENV spring.datasource.url=jdbc:mysql://$datasourceUrl/pp
ENV spring.jpa.hibernate.ddl-auto=$jpaAuto
EXPOSE 8080
RUN rm -fr /usr/local/tomcat/webapps/ROOT
COPY target/patient-portal-flow-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
