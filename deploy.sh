#!/bin/bash
set -e

echo "✅ .env 파일 다운로드"
aws s3 cp s3://ldap-ci-secrets/.env /home/ubuntu/.env

echo "🧹 기존 컨테이너 정리"
docker rm -f ldap-app || true
docker rmi 095215751727.dkr.ecr.ap-northeast-2.amazonaws.com/ldap:latest || true

echo "🔐 ECR 로그인"
aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 095215751727.dkr.ecr.ap-northeast-2.amazonaws.com

echo "📦 이미지 pull"
cd /home/ubuntu/ldap-app
docker pull 095215751727.dkr.ecr.ap-northeast-2.amazonaws.com/ldap:latest

echo "♻️ ldap-app 재시작"
docker compose -f docker-compose.yml down
docker compose -f docker-compose.yml up -d

echo "=====================배포완료====================="