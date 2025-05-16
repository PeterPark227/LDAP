# ✅ 1. 경량 JDK 기반 이미지 사용
FROM openjdk:11-jdk-slim

# ✅ 2. JAR 복사 (Docker build context 기준 경로)
COPY ldap-org-auth-system/build/libs/app.jar /app/app.jar

# ✅ 3. 작업 디렉토리 설정 (선택적이지만 깔끔함)
WORKDIR /app

# ✅ 4. 컨테이너 시작 시 실행할 명령어
ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-jar", "app.jar"]