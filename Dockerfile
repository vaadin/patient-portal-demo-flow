FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG datasourceUrl=localhost:3306
ARG jpaAuto=none
EXPOSE 8080
ENV JAVA_OPTS="-Dspring.datasource.url=jdbc:mysql://$datasourceUrl/pp -Dspring.jpa.hibernate.ddl-auto=$jpaAuto"
ARG JAR_FILE=target/patient-portal-flow-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} patient-portal.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/patient-portal.jar"]
