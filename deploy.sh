#!/bin/bash

# 1. S3에서 .env 파일 다운로드
aws s3 cp s3://ldap-ci-secrets/.env /home/ubuntu/.env

# 2. .env 적용 및 컨테이너 재시작
cd /home/ubuntu/app
docker pull 095215751727.dkr.ecr.ap-northeast-2.amazonaws.com/ldap-org-auth-system:latest
docker stop ldap-app || true
docker rm ldap-app || true

# 3. Docker 컨테이너 실행
docker run -d \
  --name ldap-app \
  --env-file /home/ubuntu/.env \
  -p 8080:8080 \
  095215751727.dkr.ecr.ap-northeast-2.amazonaws.com/ldap-org-auth-system:latest