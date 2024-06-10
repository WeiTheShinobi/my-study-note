# 淺談 Rust 的 marco

相較於 C，Rust 中的 marco 並不是單純的替換，而是生成抽象語法樹，容易避免意料之外的 bug。marco 會在編譯器的 parse 階段進行。

```rust
marco_rules! vec {
  ($($e:expr), *) => {{
    let mut v = Vec::new();
    $(v.push($e);)*
    v
  }}
}

vec![]
```

- Procedural Marco：在 compile 時，轉換成  token stream，取代或添加在原本的 token

derive, function-like, attribute

```rust 
extern crate proc_marco;
```

Compiler 特別給我們的 crate

proc_marco, proc_marco_derive, proc_marco_attr

```rust
[#proc_marco_derive(name_you_want)]
// pub is necessary
pub fn derive(input: TokenStream) -> TokenStream {
  // 解析成 ast
  let ast = parse_marco_input!(input as DeriveInput);
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

編寫 procedural marco 會大量用到 syn package，這個 package 提供關於 syntax tree 的相關功能。

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
    }
  };
```



### Reference

[Procedural Macros - The Rust Reference (rust-lang.org)](https://doc.rust-lang.org/reference/procedural-macros.html)
