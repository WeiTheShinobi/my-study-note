# Compilers

## 概述

一開始，人類使用0, 1序列來編寫程式，明確告訴電腦要怎麼做，這樣容易出錯而且難讀，後來人們有了組合語言，機器指令的別名，還有 micro（就是 C 語言中那個 `#define`），用程式寫出程式，然後不斷發展新特性至今。

編譯器的用途：

IDE 在你一邊寫 Code 時就動態的替你檢查語法錯誤，做一些提高生產力的工具，學習計算思維、數學

寫一套規則讓程式做最好的選擇，而不是自己手動配置，像是 sql、寄存器

硬體也需要編譯器生成

編譯器把一個語言翻譯成另一個等價的語言，

並行和記憶體階層可以加速

編譯器的用途

詞法分析、語法分析、中間代碼生成、優化、生成目標程式碼

編譯器先把程式碼分解開來，像是變數、每個程式語言的關鍵字（if, for...）、常數、運算符等等，還要處理空白和註解，生成語法樹，還要檢查文法有沒有正確，不然就會出現 Syntax Error，還有類型檢查，不同的類型可能需要隱性轉型，像是整數和浮點數運算，或者就是報錯，生成中間程式碼、最佳化，生成目標程式碼。

## 詞法分析

詞法分析將輸入拆分成詞法單元

有限狀態機，詞法分析可以畫出狀態轉換圖，照著這個圖施工就可以非常的舒服，狀態轉換圖有初態、終態，而這些用線來連著，線有方向性，只要一直跑下去到達終態即可。



NFA to DFA

---

## Parsing

top-down parsing

對照文法，把 token 一步一步轉換

你可以用回朔法把每個都嘗試一次

但是這樣很沒效率



預測分析：



ll1分析



- Bottom-up parsing

Top-down 反過來做

本來是將非終結符轉換

但現在是反過來

文法：A -> a ，當你讀取到 a 時，轉換成 A

我們可以使用 shift-reduce parsing

有兩個動作 shift, reduce

現在想像一個指標為 `|`，輸入 `abcdefg`

每次都可以選擇要 shift or reduce

假如是 shift -> `a|bcdefg`，就是指標往前

如果是 reduce

ab -> AB

`ab|cdefg` -> `AB|cdefg`

僅僅能 reduce 指標左邊的符號

這兩種動作會遇到問題

1. shift-reduce coflict 同時可以選擇兩者，需要一些除錯即可
2. reduce-reduce conflict 可以執行兩種 reduce，嚴重的錯誤

我們要確保只有一條正確的道路

而不是 ambiguity

我們可以用 stack 這個資料結構來處理每個 handle

我們要處理的一定會在 stack 最上方

> 畫出狀態機，只是這次還加上了指標的位置

- 確保 item 是合法的

檢測他來決定要 shift 還是 reduce

- SLR parsing

SLR 不會有 conflict，有的話就不是 SLR

> 定義 function Follow(x)
>
> 表示 x 的接續著的下一個非終結符號集合

- example

> 文法：E -> E * E | E + E
>
> 狀態：
>
> E -> E. + E
>
> E -> E * E.
>
> 這時候發生 conflict，究竟要 reduce E * E 還是 shif + ？
>
> 這裡就需要去處理

演算法：

- DFA : `M`
- Let `A | Z` be current configuration
- Run `M` on current stack `A`
- if `M` accecpt `A` with items `I`, let `a` be next input
  - `shift ` if `X -> B.ay ∈ I`
  - `reduce` if `X -> B. ∈ I` and `a ∈ Follow(X)`
  - report parsing error if neither applies
