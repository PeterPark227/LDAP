FROM openjdk:11-jdk-slim
COPY ldap-org-auth-system/build/libs/app.jar app.jar
ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-jar", "app.jar"]