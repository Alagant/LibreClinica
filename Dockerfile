FROM maven:3.5.0-jdk-8 as builder
LABEL maintainer="Lucio M. <lucioric2000@hotmail.com>"
MAINTAINER   Lucio M. <lucioric2000@hotmail.com>
ARG ENVIRONMENT
WORKDIR /libreclinica
RUN echo environment variable: $ENVIRONMENT
COPY . .
COPY docker/datainfo_docker_${ENVIRONMENT}.properties /libreclinica/core/src/main/resources/org/akaza/openclinica/datainfo.properties
COPY docker/datainfo_docker_${ENVIRONMENT}.properties /libreclinica/web/src/main/resources/org/datainfo.properties
RUN mvn -B clean install
RUN find /libreclinica -type f -name "*.war"
RUN ls /usr/src
# RUN mvn clean install  sonar:sonar -Dsonar.host.url=http://20.115.71.236:8182/ -Dsonar.login=squ_07b2feeb980836a23bd8924dbf69b2304143370e -Dsonar.projectKey=libreclinica -Dsonar.projectName=libreclinica -Dsonar.projectVersion=1.0

FROM tomcat:9-jdk8
LABEL maintainer="Lucio M. <lucioric2000@hotmail.com>"
MAINTAINER   Lucio M. <lucioric2000@hotmail.com>
ARG ENVIRONMENT
WORKDIR /libreclinica

#Environment variables
ENV M2_HOME='/usr/src/mymaven'
ENV PATH="$M2_HOME/bin:$PATH"
#obtains Maven for this image
#COPY --from=0 $M2_HOME $M2_HOME

# /SampleWebApp
COPY SampleWebApp.war /usr/local/tomcat/webapps/SampleWebApp.war
COPY docker/datainfo_docker_${ENVIRONMENT}.properties /usr/local/tomcat/libreclinica.config/datainfo.properties
COPY docker/index_${ENVIRONMENT}.html  /usr/local/tomcat/webapps/ROOT/index.html
COPY docker/web.xml /usr/local/tomcat/webapps/ROOT/WEB-INF/web.xml
#RUN mkdir -p /usr/local/tomcat/libreclinica.data
# /LibreClinica/
COPY --from=builder /libreclinica/web/target/LibreClinica-web-1.2.0.war  /usr/local/tomcat/webapps/LibreClinica.war
RUN find /usr/local/tomcat/webapps/ -type f -name "*.war"
###
COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY docker/manager_context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml
#COPY --from=builder /libreclinica/ws/target/LibreClinica-ws-1.2.1.war /usr/local/tomcat/webapps/LibreClinica-ws-1.2.1.war
