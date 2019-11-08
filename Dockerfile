FROM maven:3.6.0-jdk-8-alpine as builder
RUN mkdir /tmp/src
COPY . /tmp/src
WORKDIR /tmp/src
RUN ["mvn", "clean", "package"]

FROM tomcat
WORKDIR /usr/local/tomcat
COPY --from=builder /tmp/src/target/survey-code-webapp-0.0.1.war /usr/local/tomcat/webapps/survey-code-webapp.war
EXPOSE 8080
