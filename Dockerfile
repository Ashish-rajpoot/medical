FROM openjdk:8-jdk
COPY ./target/medical.war medical.war
CMD [ "java","-jar","medical.war"]
