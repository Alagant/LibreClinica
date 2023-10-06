FROM maven:3.5.0 as builder
WORKDIR /vhuezo
COPY . .
COPY pom.xml .

RUN mvn clean install

FROM tomcat:9.0
WORKDIR /vhuezo

COPY --from=builder LibreClinica-ws-1.2.0.war webapps/LibreClinica-ws-1.2.0.war

#COPY datainfo.properties.example libreclinica.config/datainfo.properties
#COPY datainfo.properties.example libreclinica-ws.config/datainfo.properties

