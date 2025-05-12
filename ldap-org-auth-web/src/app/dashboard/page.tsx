// dashboard.tsx
"use client"

import { useEffect, useState } from "react"

export default function Dashboard() {
  const [message, setMessage] = useState("")

  useEffect(() => {
    const token = localStorage.getItem("Authorization")
    fetch("${process.env.NEXT_PUBLIC_API_BASE_URL}/api/hello", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(res => res.text())
      .then(data => setMessage(data))
  }, [])

  return <div>{message}</div>
}