FROM maven:3.8-openjdk-8 as builder
WORKDIR /libreclinica
COPY . .
COPY pom.xml .

RUN mvn clean install

FROM tomcat:9.0-jdk8
WORKDIR /libreclinica

COPY --from=builder /libreclinica/ws/target/LibreClinica-ws-1.2.0.war /usr/local/tomcat/webapps/LibreClinica-ws-1.2.0.war

COPY datainfo.properties.example /usr/local/tomcat/webapps/libreclinica.config/datainfo.properties
COPY datainfo.properties.example /usr/local/tomcat/webapps/libreclinica-ws.config/datainfo.properties



