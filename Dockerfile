FROM maven:3-openjdk-11 as builder
WORKDIR /vhuezo
COPY . .
COPY pom.xml .

RUN mvn -f pom.xml clean package

FROM openjdk:11

COPY --from=builder multistagebuild-1.0-SNAPSHOT-jar-with-dependencies.jar multistagebuild-1.0-SNAPSHOT-jar-with-dependencies.jar

ENTRYPOINT ["java", "-cp", "multistagebuild-1.0-SNAPSHOT-jar-with-dependencies.jar", "com.scalabledeveloper.multistagebuild.App"]
