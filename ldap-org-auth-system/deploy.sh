#!/bin/bash

echo "[STEP 1] 기존 컨테이너 종료 및 삭제"
docker stop ldap-web || true
docker rm ldap-web || true

echo "[STEP 2] 기존 이미지 삭제 (옵션)"
docker rmi 095215751727.dkr.ecr.ap-northeast-2.amazonaws.com/ldap:latest || true

echo "[STEP 3] 최신 이미지 풀"
aws ecr get-login-password --region ap-northeast-2 \
  | docker login --username AWS --password-stdin 095215751727.dkr.ecr.ap-northeast-2.amazonaws.com

docker pull 095215751727.dkr.ecr.ap-northeast-2.amazonaws.com/ldap:latest

echo "[STEP 4] 컨테이너 실행"
docker run -d \
  --name ldap-web \
  -p 80:8080 \
  095215751727.dkr.ecr.ap-northeast-2.amazonaws.com/ldap:latest

echo "[완료] Spring Boot 앱이 EC2에서 실행 중입니다!"