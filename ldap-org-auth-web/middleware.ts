import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server'
import jwt from 'jsonwebtoken'

const PUBLIC_PATHS = ['/', '/login', '/auth/login']

export function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl

  // 퍼블릭 경로는 인증 없이 허용
  if (PUBLIC_PATHS.includes(pathname)) {
    return NextResponse.next()
  }

  const token = request.cookies.get('Authorization')?.value

  if (!token) {
    // 로그인 페이지로 리다이렉트
    return NextResponse.redirect(new URL('/login', request.url))
  }

  try {
    // JWT 토큰 유효성 확인
    jwt.verify(token, process.env.JWT_SECRET || 'default_secret')
    return NextResponse.next()
  } catch (err) {
    console.error('JWT 검증 실패:', err)
    return NextResponse.redirect(new URL('/login', request.url))
  }
}

export const config = {
  matcher: ['/dashboard/:path*', '/api/hello'],
}
