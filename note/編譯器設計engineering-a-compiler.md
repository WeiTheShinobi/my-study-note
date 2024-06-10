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

