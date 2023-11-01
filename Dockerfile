FROM maven:3.5.0-jdk-8 as builder
LABEL maintainer="Victor H. <huezohuezo.1990@gmail.com>"
MAINTAINER   Victor H. <huezohuezo.1990@gmail.com>
WORKDIR /libreclinica
COPY . .
COPY pom.xml .
RUN mvn clean install
RUN find /libreclinica -type f -name "*.war"
# RUN mvn clean install  sonar:sonar -Dsonar.host.url=http://20.115.71.236:8182/ -Dsonar.login=squ_07b2feeb980836a23bd8924dbf69b2304143370e -Dsonar.projectKey=libreclinica -Dsonar.projectName=libreclinica -Dsonar.projectVersion=1.0

FROM tomcat:7-jdk8-slim
LABEL maintainer="Victor H. <huezohuezo.1990@gmail.com>"
MAINTAINER   Victor H. <huezohuezo.1990@gmail.com>
WORKDIR /libreclinica
# /SampleWebApp
COPY SampleWebApp.war /usr/local/tomcat/webapps/SampleWebApp.war
COPY datainfo.properties /usr/local/tomcat/libreclinica.config/datainfo.properties
# /LibreClinica/
COPY --from=builder /libreclinica/web/target/LibreClinica-web-1.2.0.war  /usr/local/tomcat/webapps/LibreClinica.war
RUN find /usr/local/tomcat/webapps/ -type f -name "*.war"
###
#COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
#COPY --from=builder /libreclinica/ws/target/LibreClinica-ws-1.2.0.war /usr/local/tomcat/webapps/LibreClinica-ws-1.2.0.war



