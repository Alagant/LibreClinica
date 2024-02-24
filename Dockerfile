FROM maven:3.5.0-jdk-8 as builder
LABEL maintainer="Lucio M. <lucioric2000@hotmail.com>"
MAINTAINER   Lucio M. <lucioric2000@hotmail.com>
#ARG ENVIRONMENT
#WORKDIR /libreclinica
#VOLUME /root/.m2
#RUN echo environment variable: $ENVIRONMENT
COPY docker/datainfo_docker_${ENVIRONMENT}.properties /libreclinica/core/src/main/resources/org/akaza/openclinica/datainfo.properties
COPY docker/datainfo_docker_${ENVIRONMENT}.properties /libreclinica/web/src/main/resources/org/datainfo.properties
COPY . .

RUN mvn -B clean install -T 100 -DskipTests

FROM tomcat:9-jdk8
LABEL maintainer="Lucio M. <lucioric2000@hotmail.com>"
MAINTAINER   Lucio M. <lucioric2000@hotmail.com>
ARG ENVIRONMENT
WORKDIR /libreclinica

#Environment variables
ENV M2_HOME='/usr/share/maven'
ENV PATH="$M2_HOME/bin:$PATH"
ARG ADMINEMAIL
#ENV ADMINEMAIL
#ENV ADMIN_EMAIL=${ADMINEMAIL}
#obtains Maven for this image
COPY --from=builder $M2_HOME $M2_HOME

#installs using Maven
COPY . .
#RUN find /libreclinica -type f -name "*.war"
#RUN /bin/bash -c "ls -lh /libreclinica"
#RUN mvn -B clean install -DskipTests

# /SampleWebApp
COPY SampleWebApp.war /usr/local/tomcat/webapps/SampleWebApp.war
COPY docker/datainfo_docker_${ENVIRONMENT}.properties /usr/local/tomcat/libreclinica.config/datainfo.properties
COPY docker/index_${ENVIRONMENT}.html  /usr/local/tomcat/webapps/ROOT/index.html
COPY docker/web.xml /usr/local/tomcat/webapps/ROOT/WEB-INF/web.xml
COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY docker/manager_context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml

# /LibreClinica/
#COPY --from=builder /libreclinica/web/target/LibreClinica-web-1.3.1.war  /usr/local/tomcat/webapps/LibreClinica.war
#RUN find /usr/local/tomcat/webapps/ -type f -name "*.war"
###
#COPY --from=builder /libreclinica/ws/target/LibreClinica-ws-1.2.1.war /usr/local/tomcat/webapps/LibreClinica-ws-1.2.1.war

RUN env
COPY --from=builder /libreclinica/web/target/LibreClinica-web-1.3.1.war  /usr/local/tomcat/webapps/LibreClinica.war
