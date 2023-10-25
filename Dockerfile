FROM 3.8-openjdk-11 as builder
WORKDIR /libreclinica
COPY . .
COPY pom.xml .

RUN mvn clean install

FROM tomcat:9.0-jdk11
WORKDIR /libreclinica

COPY --from=builder /libreclinica/ws/target/LibreClinica-ws-1.2.0.war /usr/local/tomcat/webapps/LibreClinica-ws-1.2.0.war

COPY datainfo.properties.example libreclinica.config/datainfo.properties
COPY datainfo.properties.example libreclinica-ws.config/datainfo.properties



