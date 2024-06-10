粗看編譯器（high-level）

> 參考自 Rust Compiler Development Guide 26 ~ 32
>
> [Overview of the compiler - Rust Compiler Development Guide (rust-lang.org)](https://rustc-dev-guide.rust-lang.org/overview.html)

- Invocation

從 command line 呼叫 rust 編譯器（或是 cargo），其中會處理 arguments，`rustic_driver`負責

- Lexing and parsing

`rustc_lexer` 將 source code 轉化爲 token

`rustc_parse` 將 token 轉換為 AST

parser 使用 recursive descent (top-down) approach

我們常用到的 marco, early-lint 都會在這個階段處理

- AST lowering

將 ast 轉換成 hir，對編譯器更有好的 ast 表示，desuragering，這個過程稱為 lowering

我們使用 hir 來：

1. 類型檢查：將 hir 找到的類型轉為編譯器使用的內部表示
2. 類型推斷
3. trait solving：配對 impl 和 trait

- MIR lowering

將 hir 轉換為 mir

在這個階段會做很多優化，在此階段 rust code 也會 monomorphized，

將所以泛型用具體類型替換並複製一份

- code generation

也可以稱作 codegen，生成執行程式碼，rustc 使用 LLVM 來 codegen，將 mir 轉換為 LLVM-IR

## 如何做到？

