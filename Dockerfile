FROM openjdk:8-alpine
VOLUME /tmp
ADD target/mmdb-java-0.0.1-SNAPSHOT.jar mmdb.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=production -jar /mmdb.jar