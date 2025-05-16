# 1. 가볍고 빠른 Node.js Alpine 이미지 사용
FROM node:18-alpine
# 2. 앱 작업 디렉토리 설정
WORKDIR /app
# 3. 모든 파일을 컨테이너에 복사
COPY . .
# 4. 의존성 설치 및 빌드
RUN npm install && npm run build
# 5. 웹 앱 포트 노출
EXPOSE 3000
# 6. 컨테이너 실행 시 앱 시작
CMD ["npm", "start"]