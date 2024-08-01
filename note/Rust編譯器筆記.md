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

將所以泛型用具體類型替換並複製一份.

- code generation

也可以稱作 codegen，生成執行程式碼，rustc 使用 LLVM 來 codegen，將 mir 轉換為 LLVM-IR

## 如何做到？

compiler 需要在編譯速度、大小、執行速度等等多個維度做出取捨。

### IR in Rust

- Token stream: produce from lexer
- AST
- HIR
- THIR
- MIR: control-flow graph，借用檢查、數據流檢查
- LLVM-IR: standard form of LLVM compiler

### Queries

編譯器會進行 incremental compilation，這可以減少重複的編譯工作。編譯器上的步驟會被 query system 紀錄，並且 cache 在 disk 中，編譯器便可以此來分辨結果與上次的變化，並且只重做有變化的部分，這就是 incremental compilation 的工作原理。

All queries are defined as methods on the `TyCtxt`) type

### ty::Ty

`rustc_middle::ty::Ty` 非常重要，

### Bootstrapping

`dogfood`，rust 的新編譯器是舊編譯器編譯的。這個行為稱作 Bootstrapping

---

- `compiler/` contains the source code for `rustc`. It consists of many crates that together make up the compiler.
- `library/` contains the standard libraries (`core`, `alloc`, `std`, `proc_macro`, `test`), as well as the Rust runtime (`backtrace`, `rtstartup`, `lang_start`).
- `tests/` contains the compiler tests.
- `src/` contains the source code for rustdoc, clippy, cargo, the build system, language docs, etc.

從 `cargo.toml` 可以觀察到資料夾的依賴關係，在 AST 和其他早期分析建構完成，query system 會開始建構，它定義於 `rustc_middle` 中。

在依賴樹的上層是`rustc_driver`和`rustc_interface`，`driver`會被 `main` 韓式

---

rust compiler 依然在從傳統的 `pass-based` 過渡到 `demand-driven`，
