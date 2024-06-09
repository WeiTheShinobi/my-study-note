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

改良：

太多重複沒效率，改成 goto[i, a] = j 就 shift 

也就是如果 |a 可以到 j，那就 shift

>  define goto[i, A] = j
>
> state i + symbol A  -> state j
>
> 即 DFA 的轉換 function

LR(1) 更加強大，即是 LR(0) + 多看一個 item

LALR(1)

## Semantic analysis

在前面的章節我們知道：

Lexical analysis 檢測不合法的 token

Parsing 檢測語法樹

現在到了前端的最後一步： Semantic analysis 要檢測剩餘的錯誤

怎麼這麼麻煩？因為前面兩步驟難以抓到某些錯誤

parsing 抓不到某些錯誤、有些語言不是 context-free

靜態分析取決於你的語言想要怎麼樣

- scope（作用域）

a scope table

`enter_scope()`

`find_symbol(x)` find current x or null

`add_symbol(x)` add symbol x to the table

`check_scope(x)` ture if x define in current scpoe

`exit_scope()`

靜態分析可能會跑很多次

- Type

what is type?

- a set of values
- a set of operations on those values

當你從寄存器拿出數據，遇到`+`時，你如何知道怎麼做？

不知道，你之所以知道是因為你定義了`int`，

而`int`遇到`+`就會做數學上的加法

型別檢查是為了確保在正確的型別上做正確的動作

- type checking

假如一個假設是對的，那結論就會是對的

> a: int + b: int -> a + b : int
>
> a 是整數，b 是整數，a+b 是整數

合理的 (sound)：

whenever it is provable that "e" is of type "T", "e" must always evaluate to a value of type "T"

"e" 是 type T, 那他就應該一直是 value of type T

- type environment

因為只有靠剛剛的假設，還是不夠，所以就加入 type environment（蠻合理的

舉例來說：有一個變數，被宣告為變數，那他到底是什麼？ `var x`

這東西有點像是上下文，幫忙判斷類型

`var x = 5`：從此我們知道這是 int

可以是個像是映射、符號表之類的東西，或者說像是一個 function

舉例：當知道 e2 是 t2，就可以從此推測知道 e1 是 t1

就像是對話的上下文

還有一種方法：

x 是 type a, y 是 type b

type a 是 type b 的 sub set

那 x 就會是 type b

這種繼承關係可以像一棵樹一樣不斷向下

## Runtime Organization

動態、靜態型別

```
w: Animal = new Cat;
（動態：Cat 靜態：Animal)
靜態就是固定的
動態可以改變
```

- Error Recovery

使用 self_type, no_type 定義特殊的情況

---

作業系統執行程式：

- 開闢空間
- 把 code 放進空間
- 找到程式進入點 (main)

想了解編譯器，你要熟悉何謂靜態（編譯時期）、動態（執行時期），

管理執行資源、儲存組織

編譯器需要：生成程式碼、編排資料

-  Activations

兩個假設：程式碼照順序執行、呼叫程式執行完會回去呼叫點

lifetime：函式的呼叫到結束。可以是巢狀的，就像大家都在 main 中

當每個函式開始生命週期，就放進 stack，當結束就移出，

那要記錄什麼內容呢？frame

當 F 呼叫 G，不只紀錄 G，還有 F

當 G 結束了，要回到 F，

所以要記錄 Activation Records：

- G
- 回去 F

編譯器產生的程式碼必須也考慮到紀錄，兩者互相依賴

現在瞭解了生命週期，那全域變數呢？或者是回傳的 object 呢？

他們不是應該在生命週期結束就消失了嗎？

不是，他們會放在 Heap 上而是 stack。

- Alignment

現代計算機是 32 bit 或 64 bit

8 bits = 1 byte

4 or 8 byte in a word

機器要對齊這些資料才能讀取，

假如一個 32-bit 的機器要讀取 "Hello"

那他會需要兩個 4 byte，而 "Hello" 會用掉 5 byte

那剩下 3 byte 呢？不該使用

某些程式語言會在字串最後加上 '\0' 來表示結束

## Code Generation

MIPS 的計算機

`$sp` : stack pointer, top of the stack is at address $sp + 4

`$a0` : accumulator  register address in MIPS



`lw reg1 offset(reg2)`: load 32-bit word from address `reg2 + offset` into reg1 （把reg2 放到 reg1

`add reg1 reg2 reg3`: reg1 <- reg2 + reg3

`sw reg1 offset(reg2)`: store 32-bit word in reg1 at address reg2 + offset （把 reg1 放到 reg2

`addiu reg1 reg2 imm`: reg1 <- reg2 + imm

`li reg imm` : reg <- imm

> imm : immediate 立即數 像是 1, 2, 3, 4....等等



`cgen(e)` : code generate

cgen(e1 + e2) = cgen(e1) + cgen(e2)

`jal label` : jump to label, save address of next instruction in $ra

`jr reg` : jump to address in register reg

- 暫存空間

執行時需要的空間取決的會花費的最大空間，

因為空間可以重複使用

NT(e1 + e2) = max(NT(e1), 1 + NT(e2))

> 一個 function call :
>
> 2 + n + NT(e)
>
> return address,
>
> frame pointer,
>
> n arguments,
>
> NT(e) location for intermediate results

- Object Layout 

物件怎麼在記憶體中表示？排在一起（classs tag, size, ptr, attributes...）

物件的動態實現？



## Optimization

- Intermediate code

source -> intermediate -> target

為何要有中間的？因為 source 太高階，我們想對某些像是寄存器之類的優化

就像是高階的組合語言

three-address code

總之這就是一種程式碼生成

- Optimization

lexcial analysis, parsing, remantic analysis,

code generation, 

最後是 Optimization

這是現代編譯器最重要的一步

在何時優化？

Intermediate，既可以跨機器，又不會太高階

確保結果是一致的，執行速度、大小

### Local optimization

優化 block，大部分編譯器會做的事情

 有些句子不需要

 x := x + 0

 x := x * 1

 有些可以被簡化

 x := x * 0

 x := 0

 y := y**y

 y := y * y

常數操作可以被直接計算替代

- 有時是危險的

cross compile 兩個浮點數相加

在不同架構可能會得出不一樣的答案

- 移除不到抵達的 code，可以更快、更小

- dead code

一個變數被宣告但用不到，可以被移除

- Cache

編譯器很會分配寄存器

考慮兩種寫法：

```python
for j in range(10):
  for i in range(10000000):
    a[i] *= b[i]
```

```python
for i in range(10000000):
  for j in range(10):
    a[i] *= b[i]
```

第二種寫法會快不少，

因為第二種寫法一直計算同一件事

可以更好地利用 cache

- 垃圾回收演算法

垃圾回收會把無法抵達的指標給釋放

- Stop and Copy 

開闢一個新的空間，

從根節點開始複製到新空間，

再來是根節點的指向的指標，

直到沒有新的節點。

清除所有舊的節點

優點：最快、解決碎片化

- Reference Counting

在指標紀錄被指向的次數，

當為零時就釋放。

缺點是不能有循環
