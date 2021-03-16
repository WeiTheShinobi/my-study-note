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

- 你可以把被測試的物件取名為SUT(System Under Test)。

## 測試3A

你應該照著這個順序寫單元測試：

- Arrange: 初始化
- Act: 執行
- Assert: 驗證

## 測試錯誤與測試失敗

- 測試錯誤是發生了錯誤了。
- 測試失敗是沒通過測試。

## 剛開始時避免去看被測試類的實作

- 避免被影響，產生先入為主。先以輸入輸出為準。

## 測試物件而不是資料結構

- 你應該測試的是有行為的物件。
- 不需要測試資料結構，就是指那些getter/setter。

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

- **注意2**：你不太需要去測試null的情況，除非有@Nullable。

# Test Doubles

如果要測試的物件裡面需要用到其他的物件怎麼辦？

直接放其他物件進去會讓測試結果不穩定，可能測試失敗也不知道是哪個單元的問題。

我們要只測試我們想要測試的，所以我們需要**假物件(mock)**來放入。

```java
private static void objATD implements objA {
    // 假物件
}
```

自己動手寫好累，當然別人也想到了，於是框架誕生了，還是用框架省力比較爽。

## 注意！

### 靜態方法

**注意：**你沒辦法斷開靜態方法的連接，而這在測試時很危險，因為他們就是黏住了。必須得注意！

### 單例模式

**注意：**測試單例模式可能會發生一件事：**同時測試時通過**，單獨測試時失敗，因為單例可以在不同之間方法溝通，讓你無法測試。

# Mockito

Mockito框架讓你不用寫一大堆的假物件累得要死，當然你不一定要用它，就像其他框架一樣，它的用途是幫助你省時省力。

## 初始化

```java
private SUT SUT;
private objA objAMock;

@BeforeEach
 public void setup(){
     objAMock = mock(objA.class);
     SUT = new SUT();
 }
```

**註解初始化**

```java
@ExtendWith(MockitoExtension.class)
class test{
    @InjectMocks
	private SUT SUT;
	@Mock
	private objA objAMock;
}
```

Mockito還有很多東西需要上網看看（例如文檔或別人整理好的），畢竟他們寫得很清楚。

可以參考的資料：

- [[Android 十全大補\] Mockito - iT 邦幫忙::一起幫忙解決難題，拯救 IT 人的一天 (ithome.com.tw)](https://ithelp.ithome.com.tw/articles/10227117)

# Test Driven Development

測試驅動開發







# 待更新

