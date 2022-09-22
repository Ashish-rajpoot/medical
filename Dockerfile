FROM openjdk:8-jdk
COPY ./target/Medical-1.0.0-SNAPSHOT.war Medical-1.0.0-SNAPSHOT.war
CMD [ "java","-jar","Medical-1.0.0-SNAPSHOT.war" ]