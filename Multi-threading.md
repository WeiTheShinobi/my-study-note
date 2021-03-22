# Multi-threading 多執行緒

> 筆記作者：葉高緯 Wei the Shinobi
>
> 參考資料：[【狂神说Java】JUC并发编程](https://www.bilibili.com/video/BV1B7411L7tE?p=1)

# Java.util.concurrent

裡面有關於多執行緒的一些東西，簡單點像是Thread或是Runable，

但相較callable略為慢一點。

# 處理程序（進程）與多執行緒（多線程）

一個處理程序可以包含多的執行緒。

Java默認兩個執行緒：main、GC

**Java真的可以開啟執行緒嗎？**

不能。

點進Thread方法可以看到他最終會調用start0，

這是一個native方法。

## 多執行緒的狀態 

從Thread的原始碼裡面可以**多執行緒的找到六種狀態**：

```java
public enum State {
    NEW,
    RUNNABLE,
    BLOCKED,
    WAITING,
    TIMED_WAITING,
    TERMINATED;
}
```



#  問題

## 多執行緒操作同一個資源

大家同時操作同一個資源可能會出問題，這時我們需要加上鎖。

synchornized 