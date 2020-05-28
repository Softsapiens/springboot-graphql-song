FROM openjdk:11-jdk-slim

ARG JAR_FILE

ADD target/${JAR_FILE} /opt/graphql-song/lib/graphql-song.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /opt/graphql-song/lib/graphql-song.jar"]