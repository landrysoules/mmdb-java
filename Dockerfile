FROM openjdk:8-alpine
VOLUME /tmp
ADD target/mmdb-java-0.0.1-SNAPSHOT.war mmdb.war