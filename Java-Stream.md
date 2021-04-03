# Java Stream

> 筆記作者：葉高緯 Wei the Shinobi

學會函數式程式設計，用流式運算來替代`for`。

# 四大函數式介面（重點、必須掌握）

現代工程師應該掌握的：lambda表達式、鍊式編程、函數式介面、Stream流式計算

`@FunctionalInterface`

**函數式介面：只有一個方法的介面。**

例如`Runnable`。

## 函數式介面

自訂輸入輸出

```java
// 回傳輸入的值
Function function = new Function<String,String>() {
    @Override
    public String apply(String str) {
        return str;
    }
};

// lambda表達式
Function<String,String> function2 = (str)->str;
```

## 斷定式介面

回傳布林

```java
// 判斷傳入值是否為空
Predicate<String> predicate = new Predicate<String>() {
    @Override
    public boolean test(String str) {
        return str.isEmpty();
    }
};

// lambda表達式
Predicate<String> predicate2 = (str)->str.isEmpty();
```

## 消費型介面

無回傳值

```java
// println傳入值
Consumer<String> consumer = new Consumer<String>() {
    @Override
    public void accept(String s) {
        System.out.println(s);
    }
};

// lambda表達式
Consumer<String> consumer2 = (str)-> System.out.println(str);
```

## 供給型介面

只有返回值

```java
// 回傳"hello"
Supplier<String> supplier = new Supplier<String>() {
    @Override
    public String get() {
        return "hello";
    }
};

// lambda表達式
Supplier<String> supplier2  = ()->"hello";
```

# 快速開始

```java
List<String> list1 = Arrays.asList("abc","adef","aijk","monkey");
List<String> list2 = list1.stream()
        .filter(s -> s.startsWith("a"))  // 過濾
        .map(String::toUpperCase)        // 將字母轉成大寫
        .sorted()                        // 排序
        .collect(Collectors.toList());   // 將流轉換成List
System.out.println(list2);
```

一個簡單的示範程式碼。

最後要記得將流轉回來用。

## 將數組轉換成流

```java
String[] array = {"Monkey", "Lion", "Giraffe", "Lemur"};
Stream<String> nameStrs2 = Stream.of(array);

Stream<String> nameStrs3 = Stream.of("Monkey", "Lion", "Giraffe", "Lemur");
```

## 將文本文件轉換成流

```java
Stream<String> lines = Files.lines(Paths.get("file.txt"));
```

## 將集合類轉成流


```java
List<String> list = Arrays.asList("Monkey", "Lion", "Giraffe", "Lemur");
Set<String> set = new HashSet<>(list);
Stream<String> streamFromSet = set.stream();
```

## 宣告以重複使用

```java
Predicate<String> x = c -> c.equals("b");
```

# 無狀態操作

狀態：操作的公共數據

我們撰寫出四大函數式介面，然後在套用進`stream`的API中，就像是組合積木一樣。

## filter()

斷言式

如字面上，它用來過濾。

## map()

它將數據轉換

```java
map(String::toUpperCase);
map(s -> s.toUpperCase());
// 兩種寫法同樣的結果
```

### 使用peek

```java
map(s -> {
    s.toUpperCase();
    return s;
});

peek(s -> {
    s.toUpperCase();
});

// 兩種寫法同樣的結果
```

如果輸入與輸出一樣，可以使用`peek`，就可以省略掉`return`。

## flatMap 多維數組的處理

`map`無法處理多維數組，

要使用`flatMap`來流中流。

# 有狀態操作

```java
limit(2);  // 取前2個元素
skip(2);   // 跳過前2個元素
distinct();  // 去除重複
sorted();  // 排序
```

# 併行操作

`parallel()`

我們之前看到的都是串行操作，

併行操作速度快，因為多個CPU同時進行，

但盡量不要執行有狀態操作，

因為順序不一定，

可能出錯。

**併行不適合操作**`LinkedList`、`BlockingQueues`、`IO` **因為不易拆分**

**適合拆分**`ArrayLists`、`HashMap`

# 排序

```java
employees.sort(
        Comparator.comparing(Employee::getGender)
        .thenComparing(Employee::getAge)
        .reversed()
);
```

# reduce

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
int result = numbers
        .stream()
        .reduce(0, (subtotal, element) -> subtotal + element);
System.out.println(result);  //21
```

