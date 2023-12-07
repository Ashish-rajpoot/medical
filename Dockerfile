FROM openjdk:8-jdk
COPY ./target/medical.war medical.jar
CMD [ "java","-jar","medical.war"]
