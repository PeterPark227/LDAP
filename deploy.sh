#!/bin/bash

echo "✅ .env 파일 다운로드"
aws s3 cp s3://ldap-ci-secrets/.env /home/ubuntu/.env

echo "📂 앱 디렉토리 생성 및 이동"
mkdir -p /home/ubuntu/app
cd /home/ubuntu/app

echo "🧹 기존 컨테이너 정리"
docker rm -f ldap-app || true
docker rmi 095215751727.dkr.ecr.ap-northeast-2.amazonaws.com/ldap:latest || true

echo "🔐 ECR 로그인"
aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 095215751727.dkr.ecr.ap-northeast-2.amazonaws.com

echo "📦 이미지 pull"
docker pull 095215751727.dkr.ecr.ap-northeast-2.amazonaws.com/ldap:latest

echo "🚀 컨테이너 실행"
docker run -d \
  --name ldap-app \
  --env-file /home/ubuntu/.env \
  -p 8080:8080 \
  095215751727.dkr.ecr.ap-northeast-2.amazonaws.com/ldap:latest