# Multi-threading 多執行緒

> 筆記作者：葉高緯 Wei the Shinobi
>
> 參考資料：[【狂神说Java】JUC并发编程](https://www.bilibili.com/video/BV1B7411L7tE?p=1)

# Java.util.concurrent

裡面有關於多執行緒的一些東西

簡單點像是Thread或是Runable，但相較callable略為慢一點。

# 處理程序（進程）與多執行緒（多線程）

一個處理程序可以包含多的執行緒。

Java默認兩個執行緒：main、GC

**Java真的可以開啟執行緒嗎？**不能。

點進Thread方法可以看到他最終會調用start0，

這是一個native方法。

## 多執行緒的狀態 

從Thread的原始碼裡面可以找到**多執行緒的六種狀態**：

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

- Synchornized 

- interface Lock

Lock的實現類：ReentrantLock、ReadLock、WriteLock

- ReentrantLock建構子可以傳入boolean來決定公平鎖
  - 公平鎖：先來後到
  - 非公平鎖（默認）：可以插隊

## Synchornized 與 Lock 的區別

1. Synchornized 是關鍵字；Lock是個Java類。
2. Synchornized 無法判斷獲取鎖的狀態；Lock可以判斷是否獲取到鎖。
3. Synchornized 會自動釋放鎖；Lock要手動。
4. Synchornized 阻塞時，另一個線程會一直等待；Lock就不一定。
5. Synchornized 可重入鎖，不可中斷，非公平；Lock可重入鎖，可以判斷鎖，非公平（可設置）。
6. Synchornized 有程式碼鎖和方法鎖，Lock只有程式碼鎖。







# 待更新

