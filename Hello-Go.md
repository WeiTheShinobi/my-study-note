# Hello-Go

一個Go的基礎筆記。

> 筆記作者：葉高緯 Wei the Shinobi

# 介紹

Go的編譯速度很快，還有內置多執行緒，類型系統簡單高效率，還有GC回收。

## goroutine

Go的多執行緒，

goroutine之間透過**通道**溝通，

不需要鎖也能操作同一個資源，

但要注意如果傳遞的是數據的指標時，

如果讀和寫是不同goroutine完成，

則需額外的同步動作。

## 鴨子類型

在GO中實現某個接口不需要去宣告，只要實現就好。

其他語言把這特性叫做鴨子類型。

GO的語言接口(interface)通常只會描述單一動作，

GO的接口更小，更利於組合。

## 類型簡單

其他語言要一層層繼承，

在GO中設計小類型然後組合成大東西。

<img src="./image/go/go01.png">



# 變量聲明

```go
var a int
```

默認值為0

```go
var c = 100
```

智慧型別

```go
d := 100
```

省去var關鍵字，最常用。

全域變量無法使用這個方法。

```go
const age int = 18
```

常量宣告，唯讀。

```go
const (
    A = 0
    B = 1
    C = 2
)
```

常量實現枚舉。

```go
const (
    A = iota // 0
    B        // 1
    C        // 2
)
```

只需要這樣寫就好，

iota會從0自動遞增。

# Function

```go
func test(a string, b int) int {
    c := 100
    return c
}
```

```go
func test(a string, b int) (int, int) {
	fmt.Print(a)
	fmt.Print(b)
	c := 100
	d := 200
	return c,d
}
```

多個回傳值的示範

```go
func main() {
   ret1, ret2 := test("abc", 200)
   fmt.Print(ret1,ret2)
}
```

**注意：你沒辦法宣告一個沒用到的變數，如果宣告了就必須要使用到。**

```go
func test(a string, b int) (c int,d int) {
	fmt.Print(a)
	fmt.Print(b)
	c = 100
	d = 200
	return
}
```

你可以**替返回值命名**。 

