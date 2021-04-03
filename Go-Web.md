# Go Web

> 筆記作者：葉高緯 Wei the Shinobi

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

處理器函式必須要實現`ServeHTTP(w http.ResponseWriter,r *http.Request)`

```go
func sayHello(w http.ResponseWriter,r *http.Request) {
   fmt.Fprintln(w,"<h1>hello</h1>")
}
```

可以配合`HTML`的寫法。

# ResponseWriter

處理器通過這個街口創建HTTP響應，創建時會用到`http.response`結構。

`ServeHTTP`的兩個參數都是引用傳遞，雖然`ResponseWriter`看起來像一個值，但實際上是個帶有結構指標的介面。

`ResponseWriter`的三個方法

- `Write`
- `WriteHeader`
- `Header`

## Write

接受一個字節數組作為參數，並寫入HTTP響應的主體中。

```go
w.Write([]byte(str))
```

## WriteHeader

接收一個HTTP狀態碼的整數，並作為返回狀態碼。

## Header

設定首部

```go
func headerExp(w http.ResponseWriter,r *http.Request){
   w.Header().Set("Location","https://www.facebook.com/")
   w.WriteHeader(302)
}
```

這段程式碼示範了跳轉到`https://www.facebook.com/`。

# cookie

```go
// 自定義cookie然後設定，科學，合理。
c1 := http.Cookie {
   Name: "cookie",
   Value: "hello",
   HttpOnly: true,
}
http.SetCookie(w,&c1)
```

```go
cookie, _ := r.Cookie("cookie")
cookies := r.Cookies()

// 取取得cookie
```

