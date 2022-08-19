FROM openjdk:17

ADD target/*.jar promexample.jar
ENTRYPOINT ["java", "-jar", "dpmp.jar"]
