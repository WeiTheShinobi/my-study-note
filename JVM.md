# JVM

> 筆記作者：葉高緯 Wei the Shinobi
>
> 參考資料：[【狂神说Java】JVM快速入门篇](https://www.bilibili.com/video/BV1iJ411d7jS)
>
> 延伸閱讀：《*深入理解 Java 虛擬機：JVM 高級特性與最佳實踐*》

# JVM 結構

<img src="./image/jvm/jvm01.png">

# 加載器與雙親委派

<img src="./image/jvm/jvm02.png">

加載器：


- 根加載器
- 擴展類加載器
- 應用程式加載器

雙親委派：

- 先找根加載器，如果沒有
- 找擴展類加載器，如果沒有
- 找應用程式加載器（我們寫的程式）
- 如果都沒有，ClassNotFoundException

# Native 關鍵字

1. 凡是帶了native關鍵字，說明Java的作用範圍達不到了，**調用底層C語言的庫**。
2. 進入**本地方法棧**(native method stack) 
3. 調用**本地方法接口JNI**(native method interface)

JNI作用：擴展Java的使用，融合不同的語言為Java所用。

Java開闢了一個標記區域：native method stack，登記Native方法，

在最終執行時，加載本地方法庫中的方法通過JNI。

# 方法區

方法區被所有線程共享，靜態變量、常量、類信息和常量池存在方法區中，

但是**實例變量**存在**堆**中，和方法區無關。

static, final, Class, 常量池

# 棧 Stack

線程等級

先進後出

# 堆 Heap

一個JVM只有一個Heap，

Heap記憶體的大小是可以調節的。

類加載器讀取類文件後，把類、方法、常量、變量放到堆中，

保存所有引用類型的真實對象。

## 三個區域

- 新生區
  - 伊甸園
  - 倖存區1
  - 倖存區2
- 老年區
- 永久區（JDK 1.8後叫元空間）

GC回收主要在伊甸區和老年區。

假設記憶體滿了就會出現OOM(OutOfMemoryError)，

### 新生區

- 誕生和死亡的地方
- 伊甸園：所有物件都在這new出來。
- 沒被GC清除的物件會不斷往下移動，直到老年區，

### 永久區

用來存放JDK自身攜帶的Class物件。Interface元數據，存儲的是Java運行時的環境。

不存在GC回收。

關閉JVM就會釋放這個區域。

- jdk 1.6 之前：永久代，常量池在方法區。
- jdk 1.7 ：永久代，但是慢慢退化了，**去永久代**，方法區在堆中。
- jdk 1.8之後：無永久代，常量池在元空間中。

## JVM 記憶體參數

```java
-Xms1024m -Xmx1024m -XX:+PrintGCDetails
```

手動調整記憶體為1024MB，

```java
-XX:+PrintGCDetails
```

> 這一行可以打印出資訊。

新生區和老年區的記憶體加起來就是JVM的記憶體。

元空間在邏輯上存在，物理上不存在。





# 待更新

