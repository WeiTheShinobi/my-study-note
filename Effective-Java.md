# Effective Java

> 書名：《*Effective Java Programming Language Guide*》
>
> 筆記作者：葉高緯
>
> 還好這本書專有名詞是用英文，不然不同的中文翻譯會看得很痛苦。

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

### 條款 7：複寫`equals()`時請遵守通用契約(general contract)

避免出錯的最簡單方法就是不複寫`equals()`

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

### 條款 8：複寫`equals()`時請總是一併複寫`hashCode()`

如果你忘記這麼做，會違反hashCode的通用契約，

在和"hash-based collections"(HashMap, HashSet, Hashtable)協作時會妨礙正確的表現。

