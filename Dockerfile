#FROM tomcat:8-jre8
#ARG datasourceUrl=localhost:3306
#ARG jpaAuto=none
#ENV spring.datasource.url=jdbc:mysql://$datasourceUrl/pp
#ENV spring.jpa.hibernate.ddl-auto=$jpaAuto
#EXPOSE 8080
#RUN rm -fr /usr/local/tomcat/webapps/ROOT
#ENV JAVA_OPTS="-Xmx2048m"
#COPY server.xml /usr/local/tomcat/conf/server.xml
#COPY context.xml /usr/local/tomcat/conf/context.xml
#COPY target/patient-portal-flow-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

FROM openjdk:8-jdk-alpine
ARG datasourceUrl=localhost:3306
ARG jpaAuto=none
COPY target/patient-portal-flow-0.0.1-SNAPSHOT.war app.war
EXPOSE 8080
ENV JAVA_OPTS="-Dspring.datasource.url=jdbc:mysql://$datasourceUrl/pp -Dspring.jpa.hibernate.ddl-auto=$jpaAuto"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.war" ]