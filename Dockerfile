FROM openjdk:8-jdk-alpine
ARG datasourceUrl=localhost:3306
ARG jpaAuto=none
COPY target/patient-portal-flow-1.0-SNAPSHOT.war app.war
EXPOSE 8080
ENV JAVA_OPTS="-Dspring.datasource.url=jdbc:mysql://$datasourceUrl/pp -Dspring.jpa.hibernate.ddl-auto=$jpaAuto"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.war" ]