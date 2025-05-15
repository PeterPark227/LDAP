# 🔹 Step 1: 베이스 이미지
FROM openjdk:11-jdk-slim

# 🔹 Step 2: JAR 파일을 컨테이너에 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ldap-org-auth-system-0.0.1-SNAPSHOT.jar

# 🔹 Step 3: 컨테이너 실행 시 실행될 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]