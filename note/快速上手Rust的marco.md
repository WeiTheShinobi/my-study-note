# 快速上手 Rust 的 marco

極快的介紹 Rust 中的 marco 並附上案例與參考連結。Rust 中的 marco 並不是單純的替換，而是生成抽象語法樹（AST），較容易避免意料之外的 bug。

marco 才能做到的事 in Rust：

- 形參的數量是可變的
- 替 `struct`, `function` 生成 function, field... 等等
- 其他 ...

---

## 宣告式巨集

```rust
#[macro_export] // 只要可見即可使用
marco_rules! vec {
  // $ 代表巨集變數
  // 類似 match 語法，匹配條件
  ($($e:expr), *) => {{
    let mut v = Vec::new();
    $(v.push($e);)*
    v
  }}
}

vec![]
```

## Procedural Marco

接收 token stream，取代或添加在原本的 token

三種 procudural marco：

- derive
- function-like：用起來像是宣告式巨集，但 procudural marco 提供更為靈活的操作。下為文檔的 example：

```rust
#[proc_macro]
pub fn make_answer(_item: TokenStream) -> TokenStream {
    "fn answer() -> u32 { 42 }".parse().unwrap()
}
```
```rust
make_answer!();
fn main() {
    println!("{}", answer());
}
```

- attribute



---



```rust
// #[derive(NameYouWant)]
[#proc_marco_derive(NameYouWant)]
// pub is necessary
pub fn derive(input: TokenStream) -> TokenStream {
  // 解析成 ast
  let ast = parse_marco_input!(input as DeriveInput);
  // let ast = syn::parse(input).unwrap();
  
  let name = &ast.ident;
  // 用於生成 TokenStream 的 marco
  // 會將輸入轉換成一堆 tokens
  let expanded = quote! {
    impl #name {
      pub fn hello() {
        println!("Hello, {}!", stringify!(#name));
      }
    }
  };
  
  expanded.into()
}
```

編寫 procedural marco 會大量用到 syn crate，這個 crate 提供關於 syntax tree 的相關功能。

```rust
[#proc_marco_derive(name_you_want)]

pub fn derive(input: TokenStream) -> TokenStream {
  let ast = parse_marco_input!(input as DeriveInput);
  let name = &ast.ident;
  
  // span 用於 diagnostic，讓你知道錯誤訊息
  let newIden = syn::Ident::new(format!("{}Something", name), ast.span());

  let expanded = quote! {
    // bad 直接 hard code 會導致同個檔案只能呼叫一次此 marco，因為重複了
    struct Something {
      
    }
    // good
    struct #newIden {
      
    }
  };
  
  expanded.into()
}
```

```rust
  let expanded = quote! {
    struct #newIden {
      // 需要明確的路徑，否則錯誤
      let a = std::cmp::min(3, 5);
      // wrong
      // let a = min(3, 5)
    }
  };
```

## 條件編譯

```rust
#[cfg_attr(debug_assertions, derive(Debug))]
struct MyStruct {
    field: i32,
}

fn main() {
    let my_struct = MyStruct { field: 10 };

    #[cfg(debug_assertions)]
    println!("{:?}", my_struct); 
  	// 在 debug 模式才會編譯
  	// cargo run --release 則不會
}
```

延伸閱讀：[Quick tip: the #[cfg_attr] attribute - an article by Chris Morgan](https://chrismorgan.info/blog/rust-cfg_attr/)

### Reference

[Procedural Macros - The Rust Reference (rust-lang.org)](https://doc.rust-lang.org/reference/procedural-macros.html)

[巨集 - Rust 程式設計語言 (rust-lang.tw)](https://rust-lang.tw/book-tw/ch19-05-macros.html)
