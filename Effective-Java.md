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

不當的使用繼承會導致脆弱的軟體

- 在 package 之內使用繼承是安全的
- 繼承專門設計來被繼承且有文件的 class 也是安全的
- 跨越 package 邊界，對具象 class 繼承是危險的

一個簡單的方法可以避免複雜的問題：裝飾者模式

不是繼承，而是在新的 class 中包含一個現有 class 的欄位

以複合替代繼承

只有在「B 是一種 A」的情況下才使用繼承

### 條款 15：除非專為繼承而設計並提供文件，否則不要使用繼承

因為繼承違反了封裝性，

所以文件必須描述它是如何做、與其它方法的關係

這與給一般的終端使用者文檔不懂，這是 self-use 形式的文檔

你可以撰寫 protected 的方法提供給 subclass

class 還必須遵守一些限制規定才能被繼承：

- 建構式不能呼叫可被覆寫的函式
- 實作 Cloneable 和 Serializable 介面，`clone()`和`readObject()`不能呼叫可被覆寫的函式
- `readResolve()`和`writeReplace()`是 protected 而不是 private

### 條款 16：盡量以介面取代抽象類別

繼承有很多不方便的地方，

而且破壞了封裝性。

我們可以使用**骨幹實作類別(AbstractInterface)**來取代抽象類別的實作程式碼，

撰寫骨幹實作類別十分簡單，一個 abstract class 實作介面，分析那些函式是基本元素，讓他們成為抽象函式，實作其他的具象方法。

骨幹實作類別的優勢在於演進比介面更容易，

當我們認為演進的容易性比程式的彈性和功能更為重要時，就使用 abstract class。

### 條款 17：interfaces 只應當被用來定義型別(types)

當 class 實現某個 interface，該 interface 就成了一個型別。

interface 不應該被用來匯出常數。

### 條款 18：優先考慮 static member classes，然後才是 nonstatic

nested class 是一種定義於其他 class 內部的 class，它應該僅為其 enclosing class (外圍類別)而存在。

nested class 有四種：

1. static member classes
2. nonstatic member classes
3. anonymous classes
4. local classes

static member class 常見的用法是作為補助用的 public class，只在與其外圍 class 結合時才有用，

舉例來說 Calulator.Operation.PLUS

如果 member class 實體需要一個 reference 指向其外圍實體，讓它成為 nonstatic，反之。

## C 建構的替代品

### 條款 19：以 classes 替代 structures

### 條款 20：以 class 繼承體系取代 unions

### 條款 21：以 classes 替代 enums

可以使用 typesafe enum

### 條款 22：以 classes 和 interfaces 替代函式指標(fubction pointers)

## 函式

### 條款 23：檢查參數的有效性(validity)

**執行函式的運算之前應該先檢查參數**，

避免 null 或其他自訂的限制，不檢查日後除錯也麻煩

可以使用 Javadoc 的`@throws`說明異常和限制

除非檢驗成本過高或計算過程中會暗自進行有效性檢驗

### 條款 24：必要時製作出「保護性拷貝」(defensive copies)

Java 用起來這麼於愉悅的一個原因是，它是一個安全的語言。

只要不使用 native methods，就不會出事。

在 immutable class 中，傳入物件欄位時使用保護性拷貝(new 新物件)，

避免物件能被修改。

**注意：傳入時不能使用`clone()`進行保護性拷貝，因為可能被傳入的是該物件的 subclass 而被攻擊。**

傳出時則可以`clone()`，因為我們能確定該物件是我們的。

你應該想想物件是否是可變的，例如長度不為0的 array。

### 條款 25：謹慎設計函式的署名〈簽名，signatures〉

程式命名應該：

- 一看就懂功能
- 風格一致
- 大眾化

例如 remove 和 delete，應該使用 remove，因為用的人多。

不要有太多的便捷函式，函式太多難以維護與使用

不要過長的參數列，不好用

可以將函式分割或使用 helper classes 傳入

參數型別 interfaces 優於 classes，靈活性更好

審慎使用 function object

(查了一下，這本書出版十多年後 Java 8 才出來，現在使用 lambda 應該是沒問題的)

### 條款 26：審慎運用 overloading

重載是靜態的，在執行時就被決定，

書中例子使用重載來判斷 interfaces 的實作，

結果卻是失敗。

子類別的重載容易導致混亂

兩個完全不同的建構式重載不會造成混亂

例如 primitive 和 reference

又或者兩個重載做著完全相同的事情

那就沒關係

### 條款 27：寧願傳回長度為 0 的 array，不要傳回 nulls

傳 null 代表要額外處理判斷 null，容易出錯

長度為 0 的 array 是不可變的，這很棒

### 條款 28：為所有 exposed API 撰寫文件註釋(doc comments)

手工撰寫 API 文件是一件繁瑣的事情，

使用 Javadoc 替你省事。

doc comment 應該簡潔描述契約，

契約應說明函式做了什麼而不是如何做

你需要描述前提、後期狀態、異常、連帶影響、多線程安全

- @param：參數
- @return：回傳
- @throw：異常，後面接上 if 表示觸發條件

## 一般編程準則

### 條款 29：將區域變數的作用域(scope)最小化

類似條款12

區域變數在使用時再宣告即可，

過早宣告可能會讓人遺忘，

日後也不好維護

while 迴圈需要注意作用域

能用 for 就用 for 吧

### 條款 30：熟悉並善用函式庫(libraries)

函式庫是很棒的，

經過理工大師的設計和測試，

至少會比自己寫的還棒。

例如自己亂寫的 random 很容易出錯

需要數學和計算機的深厚背景

總之，不要重複造輪子，用大師造好的。

### 條款 31：如需精確運算結果，請勿使用 float 和 double

float 和 double 並不提供精確的結果，

尤其不要拿它們來算錢

解決方法是使用 BigDecimal、int 或 long

BigDecimal 有兩個缺點：不方便和速度較慢

### 條款 32：如果其他型別更適合，就不要使用字串(strings)

如果有更適合的 value 型別可用，不論是 primitive 或 object reference，就用它們。

字串不適合替代 enum，typesafe enums 或 int 更適合

### 條款 33：注意字串接合(string concatenation)的效率

大量連接字串使用 StringBuffer 或 StringBuilder

因為字串不可變，直接用 + 大量連接要不斷創建實體

除非你不在乎效率

### 條款 34：透過 interfaces 使用物件

```java
// 正確
List obj = new Vector();

// 錯誤
Vector obj = new Vector();
```

使用介面讓程式需要改動時更有彈性

### 條款 35：優先使用 interfaces，然後才考慮 reflection

使用反射的神奇力量是需要付出代價的：

- 失去編譯期型別檢查的所有好處
- 程式碼笨拙又冗長
- 效率更慢

### 條款 36：審慎運用 native 函式

### 條款 37：審慎進行最佳化(Optimize)

不要最佳化的警語比 Java 語言出現至少早了20年

最佳化的盲點是

程式把80%的執行時間花在20%的程式碼上

你不知道你最佳化的那部分到底重不重要

與其寫出「快速程式」，不如寫出「優質程式」

### 條款 38：遵守普遍的命名習慣(naming conventions)

切記文法性的命名

- 例如 -able 結尾
- 執行動作使用動詞
- 回傳 boolean 以 is 開頭
- 回傳某物件的屬性，用名詞或 get 開頭
- 承上，如果是 JavaBean，一定用 get 開頭
- 轉換型別，命名為 toType
- 工廠常用的名字 valueOf 和 getInstance

當然命名應該以所在團隊為主

## 異常

### 條款 39：只在異常情況下才使用異常機制

濫用異常不僅效率慢，還可能導致無法捕獲的 bug。

狀態測試優於傳回特異值

### 條款 40：對可複狀態(recoverable conditions)使用可控式異常(checked exceptions)，對編程錯誤使用執行期異常(runtime exceptions)

Java 提供三種 throwables：

- checked 可控式異常
- runtime 執行期異常
- errors 錯誤

runtime 和 errors 都沒有必要且不該被捕獲

errors 很嚴重，你也不該實現他的子類

checked 用於狀態可被復原

### 條款 41：不要濫用可控式異常(checked exceptions)

### 條款 42：盡量使用標準異常(standard exceptions)

複用異常更容易被閱讀和使用

- IllegalArgumentException
- IllegalStateException

使用哪種異常並不是嚴格規定

### 條款 43：拋出與其抽象層(abstraction)相應的異常

透過**異常轉換**

將低階異常轉變成高階抽象層的異常

```java
catch (LowerLevelException e) {
    throw new HighLevelException(...);
}
```

### 條款 44：對每一個函式所拋出的每一個異常詳加說明

### 條款 45：以詳細的訊息紀錄「失敗情況下捕獲的異常」

必須要寫好說明，出現異常時才知道如何修改

### 條款 46：努力保持 failure atomicity

### 條款 47：不要輕忽異常

不要把整段程式碼都用捕獲異常包起來

至少也要寫下說明

## 執行緒

### 條款 48：同步存取(synchronize access)共享之可變資料(shared mutable data)

Java 語言保證對單一變數的讀寫操作是原子性的(不可切割)

但也不就這樣不使用 synchronize，

因為有原子性不代表有可見性(可以被所有 thread 同時看見)

### 條款 49：避免過度使用同步機制(synchronization)

為了避免死結，

不要再同步區域呼叫 alien method

或者說

盡可能限制同步區的工作量

### 條款 50：絕對不要在迴圈之外喚起`wait()`

### 條款 51：不要依賴執行緒排程器

### 條款 52：以文件說明你的多緒安全性

### 條款 53：避免使用執行緒尋組

## 序列化

### 條款 54：審慎實現 Serializable

### 條款 55：考慮使用自訂的序列化格式

### 條款 56：保護性地寫一個`readObject()`

### 條款 57：必要時提供一個`readResolve()`

