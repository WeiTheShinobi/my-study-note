[你所不知道的 C 語言：編譯器原理和案例分析 (2018-08-25) - YouTube](https://www.youtube.com/watch?v=Cf9kXtLuQZ8&ab_channel=.GUTS)

## 為什麼需要 self-host （編譯自己）？

因為需要驗證編譯器是否正，self-host 可以簡化這個流程

bootstrapping problem 有三種方法：

1. 徒手寫出機器碼
2. 用另一個成熟的編譯器來編譯
3. 利用 interpreter 建立 translator

## self-host 有多難？

Clang 花了三年才完成 self-host，五十五萬行，因為編譯器的功能需要完整

source code -> IR -> machine code  -> relocation

IR：中間表示式

為何要有 IR？

假如有 n 種 source code, m 種 machine code，你會需要實作 nm 種作法，非常麻煩。

但沒有什麼事情是加一層不能解決的，如果有 IR 當作一個中間層，可以大幅減少需要做的事情，

n 種 source code 只要做到 IR，IR 轉成 machine code：

只需要 n+m 次實作。

## machine code

把 IR 轉成 machine code，

按照對應的處理器架構轉換，也會有系統呼叫，

EIF Header : program header, section header, segment, section

最後對需要重新定位的 symbol 作 relocation

---

動態連結

## 虛擬機器

process visual machine : JVM, EVM

system visual machine : VisualBox

編譯器和虛擬機器有很大關連，相互影響