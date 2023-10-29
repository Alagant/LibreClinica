FROM maven:3.5.0-jdk-8 as builder
MAINTAINER huezo
WORKDIR /libreclinica
COPY . .
COPY pom.xml .

# RUN mvn clean install  sonar:sonar -Dsonar.host.url=http://20.115.71.236:8182/ -Dsonar.login=squ_4101c86535f983bb3c5a91fc00f7e8b0c2df3bd1 -Dsonar.projectKey=test -Dsonar.projectName=vhuezo -Dsonar.projectVersion=1.0

RUN mvn clean install

FROM tomcat:7-jdk8-slim
MAINTAINER huezo
WORKDIR /libreclinica

COPY datainfo.properties /usr/local/tomcat/libreclinica.config/datainfo.properties

COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml

COPY --from=builder /libreclinica/ws/target/LibreClinica-ws-1.2.0.war /usr/local/tomcat/webapps/LibreClinica-ws-1.2.0.war

#COPY datainfo.properties /usr/local/tomcat/webapps/libreclinica.config/datainfo.properties
#COPY datainfo.properties /usr/local/tomcat/webapps/libreclinica-ws.config/datainfo.properties





