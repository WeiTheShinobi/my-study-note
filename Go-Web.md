# Go Web

# Web 應用

- 程式向發送命令請求的客戶端返回HTML，客戶端像用戶展示喧染後的HTML。
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

`net/http`庫提供了一個多路複用器，





