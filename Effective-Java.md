# Effective Java

> 書名：《*Effective Java Programming Language Guide*》
>
> 筆記作者：葉高緯
>
> 還好這本書專有名詞是用英文，不然不同的中文翻譯會看得很痛苦。
>
> 這本書提出一些不變的通則。

## 創建與銷毀物件

### 條款 1 ：考慮以**靜態工廠方法**取代建構式

優點：

- 方法有名稱易讀，而且比overload的建構式更好調用。
- 可以預先創建好實體。
- 你可以**傳回介面**而非物件實體。
- 可以傳回nonpublic class

缺點：

- 如果沒有public或protected建構式，將無法被子類化。
- 可能無法明顯和其他的靜態方法區區，在API文檔中也不像建構式那麼引人注目。

可以透過命名規範來改善，例如：`valueOf()`、`getInstance()`

### 條款 2 ：以private建構式厲行singleton〈單例〉性質

singleton是指**只能被實體化一次**的class

方法一

```java
public static final Test INSTANCE = new Test();
```

方法二

```java
private static final Test INSTANCE = new Test();

public static Test getInstance() {
    return INSTANCE;
}
```

兩種方法皆把建構式私有化，第二種方法日後修改較有彈性。

### 條款  3：以private建構式厲行不可實體化性質(noninstantiability) 

有時你想寫個靜態組成的類別，

不希望它被實體化，

將**建構式私有化**。

不要把類別變成abstract class，

因為可能會被繼承。

### 條款 4：避免創建重複物件(duplicate objects)

盡量讓程式碼重複使用(reuse)

不重複創建一旦被計算出來就決不會再被更改的物件

### 條款 5：消除老舊的object references

有垃圾回收的語言並不表示你不需要管理記憶體

例如在array中，

某些物件不會再用到，

但是GC並不會把它清除，

嚴重可能導致OOM。

要手動釋放設定成`null`

另一個memory leak常見的原因就是caches

### 條款 6：避免使用finalizers

## 通用於所有物件的函式

雖然object是個concrete class，其主要設計目的卻是為了擴展，

因為它所有的nofinal函式都是為了將來被override。

override這些函式應該遵守通用契約，

否則日後會很困擾。

### 條款 7：覆寫`equals()`時請遵守通用契約(general contract)

避免出錯的最簡單方法就是不覆寫`equals()`

遵守以下通用契約：

- 反身性 reflexivce

物件與自身相等

- 對稱性 symmetric

兩個物件對於彼此是否相等取得一致

- 遞移性 transitive

A是B，B是C，那A必定是C

- 一致性 consistent

多次呼叫得到的結果一致

- `x.equals(null)`必定回傳`false`

```java
if (!(obj instanceof MyType))
    return false;
```

以`==`檢查引數是否為物件自身的reference

以`instanceof`檢查引數是否為正確型別

完成撰寫時記得問自己：它有對稱性、反身性、遞移性嗎？

### 條款 8：覆寫`equals()`時請總是一併覆寫`hashCode()`

如果你忘記這麼做，會違反hashCode的通用契約，

在和"hash-based collections"(HashMap, HashSet, Hashtable)協作時會妨礙正確的表現。

**兩個相等的物件必須具有相等的hash碼**

如果你沒覆寫`hashCode()`，就會失敗。

- 不好的寫法

```java
public int hashCode() {
    return 42;
}
```

這是合法的，但很爛，

會讓hash table變成linked lists

O(n)變成O(n^2)

要點：

1. 將一個非0常數儲存於int result變數中
2. 對物件中的每一個被`equals()`所考慮的欄位`f`進行以下處理：
   1. 對這個欄位計算出型別為`int`的hash碼`c`：
      1. 如果是`boolean`，計算`(f ? 0 : 1)`
      2. 如果是`byte`,`char`,`short`或`int`，計算`(int)f`。
      3. 如果是`long`，計算`(int)(f ^ ( f >>> 32))`。
      4. 如果是`float`，計算`Float.floatToIntBits(f)`。
      5. 如果是`double`，計算`Double.doubleToLongBits(f)`，然後再按照`long`的處理方式處理。
      6. 如果是object reference，而且class的`equals()`透過遞迴比較，那也遞迴呼叫`hashCode()`。如果要更複雜，使用正則表達式。如果是`null`回傳0。
      7. 如果是`array`，按照個別方法計算後將數值組合。
   2. 將剛剛算出來的hash碼`c`按公式組合到`result`中：`result = 37*result + c;`
3. 傳回`result`
4. 檢查一下

選擇乘以37這個奇質數，和第一步選擇非0常數都是為了減少碰撞可能。

如果你的class不可變，而且計算代價很大，你可以考慮將 hash 碼緩存於物件之中待用

如果你認為某型別的大多數物件將被作為 hash 鍵，那你應當在創建實體時就計算 hash 碼。

### 條款 9：總是覆寫`toString()`

class名稱 + @ + 不帶正負號的十六進制 hash 碼

這種東西好讀嗎？

`println()`、`+`、`assert()`時`toString()`會被自動喚起。

### 條款 10：審慎地覆寫`clone()`

> 建議參考`java.util.collection`的原始碼，例如HashMap或ArrayList的clone作法

`clone()`會回傳一個欄位逐一拷貝的副本物件

如果你要 clone 一個 array

而你只是`return super.clone()`

那array元素的記憶體參考還是指向原本的物件。

### 條款 11：考慮實現Comparable

`compareTo()`並非在object中，

而是在`java.lang.Comparable`

如果你正在撰寫一個具有明顯內在次序的 value class

**強烈建議**你實現這個 interface

極小的努力就可以獲得巨大的能量

撰寫`compareTo()`和撰寫`equal()`差不多，

但有幾個差異，

不需要在型別轉換前檢查型別，

不一樣就拋出異常，

如果是 null 也拋出異常。

## 類別與介面

本章的許多守則將使你的 classes 和 interface 更有用、強固、靈活。

### 條款 12：將classes和其成員的可存取性最小化

模組的好壞，在於模組隱藏其內部資料與實作的程度，

模組和模組之間只透過 APIs 進行通訊，隱藏細節。

稱為 information hiding 或 encapsulation

Java 語言有許多設施可以實現隱藏，

其中之一便是**存取控制(access control)**。

盡可能使成員不被外界存取，

根據用途盡量使用最低的存取級別

頂層的 classes 和 interface 只可能使用 package-private(default)、public

讓 public class 變成 package-private ，

讓以後修改更方便，

因為是包內實作而不是API。

public class 不該擁有 public 欄位，

除非是 public static final 表示常量。

**注意：長度不為0的 array 總是可變的，這會導致安全漏洞。**

```java
// 不安全
public static final Type[] VALUES = {...};
```

```java
// 正確示範
private static final Type[] PRIVATE_VALUES = {...};
// 方法1
public static final List VALUES = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));
// 方法2
public static final Type[] values() {
    return (Type[]) PRIVATE_VALUES.clone();
}
```

### 條款 13：偏愛不變性(immutability)

immutable class 就是實體不能被修改的 class

資訊皆在創建之初提供

例如 String、包裝類、BigInteger、BigDecimal

不可變物件不易犯錯且更安全

- 不提供修改物件內容的方法
- 不可被覆寫，可以將 class 加上 final 修飾
- final 所有欄位
- private 所有欄位
- 確保可變物件不會被取得參考，避免被修改，在建構式、存取式和`readObject()`中使用保護性拷貝〈回傳物件副本〉。

immutable object 是執行緒安全的

String 是個不可變物件

```java
String test = "";
for (int i = 0; i > 100; i++) {
    test += "test";
}
```

上面這段程式碼，

因為 String 是個不可變物件，

重複一百次就要創建一百個實體，

非常沒效率，

所以 String 製作了 StringBuffer

要實現不可變物件可能也要解決這個問題。

不可變物件序列化要明確提供一個`readObject()`

你應該盡量讓你的數值物件不可變，不需要為每個 getter 都提供一個 setter

### 條款 14：優先考慮複合(composition)，然後才是繼承(ingeritance)

**本條款討論的問題不適用於介面繼承**

