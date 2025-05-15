FROM openjdk:11-jdk-slim

# build context 기준 경로는 리포 루트이므로 이렇게 지정
COPY ldap-org-auth-system/build/libs/app.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]