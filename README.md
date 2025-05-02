# LDAP Organization Authentication System

LDAP 기반 조직도 및 사용자 인증 시스템

## 프로젝트 구조
LdapProject/ ├── ldap-org-auth-system/ (Spring Boot - LDAP 인증, 조직 관리) ├── ldap-org-auth-web/ (Next.js - 프론트엔드, 로그인/메인 페이지)

## 개발 환경

- Java 11
- Spring Boot 2.7.18
- Next.js 14
- Node.js 20.x

## 빌드 방법

### Frontend (Next.js)
cd ldap-org-auth-web
npm install
npm run dev

### Backend (Spring Boot)

```bash
cd ldap-org-auth-system
./gradlew bootRun
