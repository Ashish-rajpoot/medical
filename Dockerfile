FROM openjdk:8-jdk
COPY ./target/medical.jar medical.war
CMD [ "java","-war","medical.war"]


<!-- FROM openjdk:8-jdk
COPY ./target/medical.jar medical.jar
CMD [ "java","-jar","medical.jar"] -->