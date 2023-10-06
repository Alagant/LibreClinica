FROM maven:maven:3-openjdk-11 as builder

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11

COPY --from=builder /usr/src/app/target/multistagebuild-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/app/multistagebuild-1.0-SNAPSHOT-jar-with-dependencies.jar

ENTRYPOINT ["java", "-cp", "/usr/app/multistagebuild-1.0-SNAPSHOT-jar-with-dependencies.jar", "com.scalabledeveloper.multistagebuild.App"]
