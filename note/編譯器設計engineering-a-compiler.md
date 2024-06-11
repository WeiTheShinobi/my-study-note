# 編譯器設計 Engineering a compiler

> WeiTheShinobi 的讀書筆記

## 一、簡介

source code -> 編譯器前端 -> 中間表示(IR) -> 編譯器後端 -> target code

- 編譯器前端：處理 source code
- 編譯器後端：處理 target code
- IR：編譯器使用一些資料結構來表達處理後的 code

輸入 source code 輸出 target code，編譯器不一定要是輸出機器碼

> 直譯器 vs 編譯器
>
> 解釋 code 產生結果, 轉化為 target code

編譯器的基本規則：

- 編譯器必須保持被編譯程序的語意

編譯器不應該隨便更改 source code 想表達的意思

- 編譯器必須以某種可察覺的方式改進輸入程序

輸出的結果要比輸入更好，不然沒有意義

---

三階段編譯器：

source code -> 前端 -> 優化器 -> 後端 -> target code

IR 可以解開前後端的耦合，搭配不同的後端，一份 source code 即可編譯出多種 target code，反之

優化器是一個 IR 到 IR 的轉換

## 二、詞法分析

lexer 在編譯器的第一階段，lexer 讀取 source code 轉換成 token stream

> 暫略

## 三、語法分析

語法分析器用來判斷一個 token stream s 是否屬於一個形式語法(formal grammer) G 定義的語言，即成員資格問題，如果能則說 G 可以推導出 s。

- 為什麼不使用正則表達式？

確實可以寫出大部分的算式，但考慮到`(`和`)`，無法寫出這兩個符號必須一樣的正則表達式，即是`number of (` = `number of )`的情況並非正則語言，這是正則表達式的限制。

### 上下文無關語法

簡稱 CFG，G 是一組規則，可以從 G 推導出的規則稱為 L(G)。

- 產生式：CFG 中的每個規則都是一個產生式
- 非終結符：產生式中使用的變量
- 終結符：出現在語句中的單詞

