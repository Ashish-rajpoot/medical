FROM openjdk:8-jdk
COPY ./target/Medical-1.0.0-SNAPSHOT.jar Medical-1.0.0-SNAPSHOT.jar
CMD [ "java","-jar","Medical-1.0.0-SNAPSHOT.jar"]