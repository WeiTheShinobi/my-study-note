# *《The Go Programming Language》*

> 筆記作者：葉高緯 Wei the Shinobi

## 第二章 程式結構

- 變數生命週期

變數會存活到**它不再可觸及**為止，

如果**區域變數的指標被指派給全域變數**，

那就不會被回收，稱為區域變數**逃離**了函式。

注意這點來讓垃圾回收正常作用。

- 資料組指派

```go
// 交換 x y 
x, y = y, x
```

- 範圍

```go
var cwd string

func init() {
    cwd, err := os.Getwd()
    // ...
}
```

需要注意：

內層的`cwd`宣告會將外層的`cwd`遮蔽，

外層的`cwd`沒有初始化，

最直接的改善方法是在此避免使用`:=`。

```go
var cwd string

func init() {
    var err error
    cwd, err = os.Getwd()
    // ...
}
```

## 第三章 基本資料型別

> 電腦基本上是在固定數量的 word 上操作，它被解讀為整數、浮點數、位元組、或記憶體位址，
>
> 然後組合成代表封包、像素、資產、詩歌，與其他東西的大型集合體。

Go 的型別分為四種：基本型別、集合型別(aggregate)、參考型別、介面型別

- 集合型別：陣列與 struct
- 參考型別：指標、slice、map、函式、channel。共通點是都間接指向程式的變數或狀態。

