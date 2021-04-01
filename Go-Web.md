# Go Web

# Web 應用

- 程式向發送命令請求的客戶端返回HTML，客戶端像用戶展示渲染後的HTML。
- 使用HTTP協議。

# 設計

1. 客戶端請求服務端
2. 服務端的多路復用器對URL檢查，並且重定向至正確的處理器
3. 處理器向模板引擎提供數據
4. 生成相應的HTML返回給客戶端

## 多路複用器

```go
mux := http.NewServeMux()

mux.handleFunc("/", index)
// 對於根目錄的請求，重定向給index處理器函數。
```

`net/http`庫提供了一個多路複用器。

## 快速開始

```go
package main

import (
	"fmt"
	"net/http"
)

func hello(w http.ResponseWriter,r *http.Request){
	fmt.Fprintln("hello")
}

func main() {
	server := http.Server{
		Addr: "127.0.0.1:8080",
	}
	http.HandleFunc("/",hello)
    
    // 啟動伺服器
	server.ListenAndServe()
}
```

處理器函式必須要傳入兩個參數`(w http.ResponseWriter,r *http.Request)`

```go
func sayHello(w http.ResponseWriter,r *http.Request) {
   fmt.Fprintln(w,"<h1>hello</h1>")
}
```

可以配合`HTML`的寫法。