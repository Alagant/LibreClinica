FROM maven:3.5.0-jdk-8 as builder
LABEL maintainer="Lucio M. <lucioric2000@hotmail.com>"
MAINTAINER   Lucio M. <lucioric2000@hotmail.com>
WORKDIR /libreclinica
COPY . .
#COPY pom.xml .
#COPY docker/logging.properties /libreclinica/web/src/main/resources/logging.properties
#COPY docker/logging.properties /libreclinica/ws/src/main/resources/logging.properties
#COPY docker/logging.properties /libreclinica/ws/src/core/resources/logging.properties
RUN mvn clean install
RUN find /libreclinica -type f -name "*.war"
# RUN mvn clean install  sonar:sonar -Dsonar.host.url=http://20.115.71.236:8182/ -Dsonar.login=squ_07b2feeb980836a23bd8924dbf69b2304143370e -Dsonar.projectKey=libreclinica -Dsonar.projectName=libreclinica -Dsonar.projectVersion=1.0

FROM tomcat:8.5-jdk8-slim
LABEL maintainer="Lucio M. <lucioric2000@hotmail.com>"
MAINTAINER   Lucio M. <lucioric2000@hotmail.com>
WORKDIR /libreclinica
# /SampleWebApp
COPY SampleWebApp.war /usr/local/tomcat/webapps/SampleWebApp.war
COPY docker/datainfo_docker.properties /usr/local/tomcat/libreclinica.config/datainfo.properties
# /LibreClinica/
COPY --from=builder /libreclinica/web/target/LibreClinica-web-1.2.0.war  /usr/local/tomcat/webapps/LibreClinica.war
RUN find /usr/local/tomcat/webapps/ -type f -name "*.war"
###
COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY docker/manager_context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml
#COPY --from=builder /libreclinica/ws/target/LibreClinica-ws-1.2.0.war /usr/local/tomcat/webapps/LibreClinica-ws-1.2.0.war
