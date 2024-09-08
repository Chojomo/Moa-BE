FROM openjdk:17-jre
COPY build/libs/moa-0.0.1-SNAPSHOT.jar moa.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "moa.jar"]