// dashboard.tsx
"use client"

import { useEffect, useState } from "react"

export default function Dashboard() {
  const [message, setMessage] = useState("")

  useEffect(() => {
    const token = localStorage.getItem("Authorization")
    fetch("http://localhost:8080/api/hello", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(res => res.text())
      .then(data => setMessage(data))
  }, [])

  return <div>{message}</div>
}