FROM openjdk:17

ADD target/*.jar dpmp.jar
ENTRYPOINT ["java", "-jar", "dpmp.jar"]
