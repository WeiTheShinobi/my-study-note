# GopherCon 2022: Jordan Neufeld - Exploring Network Programming by Building a Toxiproxy Clone

> 筆記：WeiTheShinobi
>
> 演講連結：[GopherCon 2022: Jordan Neufeld - Exploring Network Programming by Building a Toxiproxy Clone - YouTube](https://www.youtube.com/watch?v=8z6okCgdREo&ab_channel=GopherAcademy)

講者是任職於 Shopify 的 SRE

一般的網站處於網路中的 Application Layer，

本篇講述的重點著重在 Application Layer 的下面一層，

即是 Transport Layer，TCP and UDP

Toxiproxy：一個用於模擬離線的工具，使用 Go 撰寫．

標準函式庫：

```go
package io

// 用這個介面讀取網路連結
type Reader interface {
  Read(p []byte) (n int, e error)
}

// 同上，寫入
type Writer interface {
  Write(p []byte) (n int, e error)
}
```

```go
package net

// 這代表一個TCP或UDP連結，它也實作Reader和Writer兩個介面
type Conn interface {
  Read(p []byte) (n int, e error)
  Write(p []byte) (n int, e error)
  Close() error
}

// 選擇協議，用這個Dial來Dial
func Dial(network, address string) (Conn, error)
...

conn, err := Dial("tcp", "golang.org:80")

// 監聽
l, err := net.Listen("tcp4", "0.0.0.0:8080")
// block直到收到請求
conn, err := l.Accept()
```

