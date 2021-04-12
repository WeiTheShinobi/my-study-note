# Go-Gin

> 筆記作者：葉高緯 Wei the Shinobi

# 快速開始

一段簡單又易懂的Go程式碼

```go
package main

import (
   "github.com/gin-gonic/gin"
)

func sayHello(ctx *gin.Context) {
   ctx.JSON(200,gin.H{
      "message" : "Hello Go",
   })
}

func main() {
   engine := gin.Default() // 返回默認的路由引擎

   // 使用GET訪問時，執行sayHello
   engine.GET("/hello",sayHello)

   // 默認端口是8080 我們可以更改
   engine.Run(":9090")
}
```

# RESTful API

| GET      | POST     | PUT      | DELETE   |
| :------- | -------- | -------- | -------- |
| 取得資源 | 新建資源 | 更新資源 | 刪除資源 |

# Go的模板引擎

- 使用{{}}包裹和標示要傳入的數據
- 使用`.`來傳入檔案

## 使用步驟

1. 定義
2. 解析
3. 渲染

```html
<p>Hello! {{ . }}</p>
```

```go
func hello(w http.ResponseWriter,r *http.Request){
   // 2. 解析模板
   t, _ := template.ParseFiles("./hello.tmpl")
   // 3. 渲染模板
   name := "胖鴨子"
   t.Execute(w,name)
}
```

得到結果

```html
Hello! 胖鴨子
```

---



```GO
t, _ := template.ParseFiles("./hello.html","./hello2.html")
t.ExecuteTemplate(w,"./hello2.html","hello")

// 可以傳入多個模板，選擇特定模板
```

## 動作

### 條件動作

```html
{{if arg}}
	some content
{{ else }}
	other content
{{ end }}
```



### 迭代動作

```html
{{ range .}}
	<li>{{ . }}</li>
{{ else }}
	<li>nothing</li>
{{ end }}
```

### 設置動作

```html
{{ with arg }}
{{ . }}
{{ end }}
```

### 包含動作

```html
{{ template "name"}}
```

在模板裡面包含另一個模板

### 定義變量

```html
{{$age := .m1.age}}
```

### range

```html
{{ range $index, $hobby := .hobby }}
```

### block

```html
{{block "content" . }}{{end}}
```

使用`block`實現模板繼承

```html
{{ template "base.tmpl" }}
{{ define "content" }}
	<p>測試</p>	
{{end}}
```



```go
t , _ := template.ParseFiles("base.tmpl","index.tmpl")
// 先寫根模板

t.ExcuteTemplate(w,"index.tmpl","hello")
```

### XSS攻擊 （跨站腳本攻擊）

永遠不要相信客戶端傳來的內容

如果客戶端傳入的訊息是惡意的JS語句

你就出事了

還好我們可以用`html/template`包

它會保護我們

# 開始使用琴酒

```go
package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	engine := gin.Default()
	engine.LoadHTMLFiles("templates/index.tmpl")  //  模板解析
	engine.GET("/index", func(context *gin.Context) {
		// H is a shortcut for map[string]interface{}
		context.HTML(http.StatusOK,"index.tmpl",gin.H{  //  模板渲染
			"title":"hello! go!",
		})
	})

	engine.Run(":8080") // 啟動Server
}
```

上面的示範只解析一個模板

如果你有一堆模板要解析怎麼辦？

總不能一個一個打吧

```go
//engine.LoadHTMLFiles("templates/index.tmpl")  //  模板解析
engine.LoadHTMLGlob("templates/**/*")  // 一次解析全部 真爽
// 關於通配符要注意你的文件放在哪
```

## 模板自定義方法

```go
// gin框架給模板添加自定義函數
engine.SetFuncMap(template.FuncMap{
   "safe" : func(str string) template.HTML{
      return template.HTML(str)
   },
})
// 方法要寫在模板解析前
engine.LoadHTMLGlob("templates/**/*")

engine.GET("/users/index", func(context *gin.Context) {
   // H is a shortcut for map[string]interface{}
   context.HTML(http.StatusOK,"users/index.tmpl",gin.H{  //  模板渲染
      "title":"<h1>Hello!</h1>",
   })
})
```

我想把傳入的數值加上`<h1>`怎麼辦？

直接傳入只會當成`string`，

這裡示範了自定義方法。

**注意：方法要寫在模板解析前**

```html
{{.title | safe}}
```

記得要修改你的模板

## 靜態文件

```go
engine.Static("/xxx","./static")
// 把/xxx轉換成./static
```

必須寫在模板被解析前

```html
<link rel="stylesheet" href="/xxx/style.css">
```

## JSON

```go
engine.GET("/", func(context *gin.Context) {
   //data := map[string]interface{}{
   // "name": "monkey",
   // "count": 2,
   //}
   data := gin.H{
      "name": "monkey",
      "count": 2,
   }
   context.JSON(http.StatusOK,data)
})
```

也可以傳送結構體

## 獲取querystring參數

```go
// 無默認值
data := context.Query("query")
// 有默認值
data := context.DefaultQuery("query","monkey")
context.JSON(http.StatusOK,data)
```

獲取路徑參數

```go
engine.GET("/:name", func(context *gin.Context) {
    name := engine.Param("name")
    context.JSON(http.StatusOK,gin.H{
        "name": name,
    })
})
```

## bind

```GO
type UserInfo struct {
	Username string `form:"name"`
	Password string `form:"password"`
}

var u UserInfo
context.ShouldBind(&u)
```

# GORM

Go的ORM

- 優點：提開開發效率
- 缺點：犧牲效能與靈活性

[GORM 指南 | GORM - The fantastic ORM library for Golang, aims to be developer friendly.](https://gorm.io/zh_CN/docs/)

