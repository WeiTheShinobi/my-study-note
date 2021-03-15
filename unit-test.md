# 單元測試

> 作者：葉高緯 Wei the Shinobi

# 測試基礎

> 本篇使用JUnit5示範。

為什麼要單元測試：

用main方法測試，不能同時運行，還要人工觀察結果，每次改動又要重新測試，又累又麻煩。

## 第一個簡單的單元測試

```java
public class MonkeyTest {

    Monkey monkey;

    @BeforeEach
    public void setup(){
        monkey = new Monkey();
    }

    @Test
    public void addTest(){
       int x = monkey.add(5,3);
       assertEquals(8,x);
    }

}
```

## 命名規則

<單元的行為或名稱>\_<單元要接受的測試>\_<預期結果>

舉例：

```java
public void reverse_singleCharacter_sameStringReturned() throw Exception {
    // 反轉字串_傳入一個字_預期回傳一個字。
}
```

## 測試錯誤與測試失敗

- 測試錯誤是發生了錯誤了。
- 測試失敗是沒通過測試。

## 避免去看被測試類的實作

- 避免被影響，產生先入為主，先以輸入輸出為準。

## 開始

### 步驟

1. setup：先設定好你需要的物件。

   ```java
    @BeforeEach
    public void setup(){
        monkey = new Monkey();
    }
   ```
   
2. 閱讀輸出並假設情況，特別是**邊界條件**。

3. 修改。

4. 最後確保所有的測試都通過。

- **注意**：就算你有100%的覆蓋率，也不代表不會有bug，你必須再次的確認**邊界條件**，這是最容易出現bug的地方。

- **注意2**：你不太需要去測試null的情況，除非有@nullable。











# 待更新

