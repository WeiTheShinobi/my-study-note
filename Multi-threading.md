# Multi-threading 多執行緒

> 筆記作者：葉高緯 Wei the Shinobi
>
> 參考資料：[【狂神说Java】JUC并发编程](https://www.bilibili.com/video/BV1B7411L7tE?p=1)

# Java.util.concurrent

裡面有關於多執行緒的一些東西

簡單點像是`Thread`或是`Runable`，但相較`callable`略為慢一點。

# 處理程序（進程）與多執行緒（多線程）

一個處理程序可以包含多的執行緒。

Java默認兩個執行緒：main、GC

**Java真的可以開啟執行緒嗎**？不能。

點進`Thread`可以看到他最終會調用`start0`，

這是一個`native`方法。

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

- Interface Lock

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

# 生產者與消費者

## 判斷等待、業務、通知

假設現在有兩條執行緒，

一條負責生產漢堡，

一條負責販賣。

一個標準的流程就是

1. **判斷等待**
2. **業務**
3. **通知**

```java
// 判斷等待
if(漢堡 != 0) {
    wait();
}
// 業務
漢堡++;
// 通知
notifyAll();
```

然後也會有個的販售者執行緒，

一旦漢堡產出就會賣掉。

```java
// 判斷等待
if(漢堡 == 0) {
    wait();
}
// 業務
漢堡--;
// 通知
notifyAll();
```

漢堡數量只會是1或0。

## 虛假喚醒

只有兩條執行緒的情況下沒問題，

但是如果有更多的執行緒可能會發生**虛假喚醒**，

```java
if(漢堡狀態) {
    wait();
}
```

這是因為 `if` 只會判斷一次，

多個生產漢堡的執行緒可能就會產出超過1個漢堡，

**解決的方法是將 `if` 改成 `while` 即可。**

```java
while(漢堡狀態) {
    wait();
}
```

## JUC版本

### 與傳統對照

- Synchroized -> Lock
- wait -> await
- notify -> signal

```java
Lock lock = new ReentrantLock();
Condition condition = lock.newCondition();
```

按照對應表將傳統的方法替換成JUC版本即可。

```java
lock.lock();
condition.await();
condition.signalAll();
lock.unlock();
```

### JUC精確喚醒

你可以建構多個監視器來達成精確喚醒，

例如我希望A -> B -> C，

但是執行緒的順序是隨機的，

我該怎麼辦？

```java
Condition conditionA = lock.newCondition();
Condition conditionB = lock.newCondition();
Condition conditionC = lock.newCondition();
```

創建多個監視器，然後在方法結束時設定你想要喚醒的執行緒。

```java
void threadA() {
    // 略
    conditionB.signal();
}

void threadB() {
    // 略
    conditionC.signal();
}

void threadC() {
    // 略
    conditionA.signal();
}
```

# 鎖的一些問題

我們執行兩條執行緒，

發送簡訊先，

但是他的方法會睡4秒，

然後是打電話。

```java
public class Lock8 {
    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(()->{
            phone.sendSms();
        }).start();

        new Thread(()->{
            phone.call();
        }).start();
    }
}
```

```java

class Phone {
    public synchronized void sendSms(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("簡訊");
    }

    public synchronized void call(){
        System.out.println("電話");
    }
}
```

現在的問題是，

輸出的結果會是如何？

是簡訊先還是電話先？

**答案是簡訊依然先**，

雖然他睡了4秒，

但是 `synchronized` 這個關鍵字**鎖的是執行方法的物件**。

而不是**物件**。

> 沒有 `synchronized` 的方法和有鎖的方法同時執行，並不會被鎖住。

---

那如果把`sendSms()`和`call()`改成靜態方法，

且將物件改成兩個分開執行會如何？

兩個不同的物件一個執行簡訊，

另一個執行打電話，

應該是打電話先，

因為簡訊方法內延遲了4秒，

```java
Phone phone1 = new Phone();
Phone phone2 = new Phone();
```

```java
    public static synchronized void sendSms(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("簡訊");
    }

    public static synchronized void call(){
        System.out.println("電話");
    }
```

輸出結果會是**簡訊先**。

因為靜態方法鎖的是`Phone.class`，

這是全局唯一的。

---

```java
 	public static synchronized void sendSms(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("簡訊");
    }

    public synchronized void call(){
        System.out.println("電話");
    }
```

一個是靜態方法，一個是普通方法，一個物件的情況，

簡訊跟打電話哪個會先呢？

答案是打電話，

因為兩者鎖的對象不一樣，

一個是`Phone.class`，

一個是`new`出來的物件，

所以互不影響，

那沒有睡的打電話就會先了。

# 集合類不安全

在併發時，

`ArrayList`不安全，

可能出現併發修改異常，

解決方案：

```java
List<String> list = new Vector<>();
List<String> list = Collections.synchronizedList(new ArrayList<>());
List<String> list = new CopyOnWriteArrayList<>();
```

`CopyOnWrite`寫入時複製（推薦使用）。

---

`set`的解決方案也是這樣，

畢竟他們都繼承了`Collections`，

補充：`set`的底層就是`map`。

```java
Map<String,String> map = new ConcurrentHashMap<>();
```

# Interface Callable\<V\>

1. 可以有返回值
2. 可以拋出異常

```java
// Interface Callable 裡的方法。
V call() throws Exception;
```

這個泛型\<V\>等於方法的返回值。

```java
// new Thread(new Runnable());
// new Thread(new FutureTask<V>());
// new Thread(new FutureTask<V>(Callable));

MyThread thread = new MyThread(); //MyThread實現了Callable介面
FutureTask futureTask = new FutureTask(thread);

new Thread(futureTask).start();
```

你可以使用`get()`來得到回傳值。

```java
Object o = futureTask.get();
```

但是這個方法**可能會發生阻塞**，

你得**放在最後**或**使用異步**。

# 常用的輔助類

## CountDownLatch

倒計時計數器，

建構子需要傳入`int`。

```java
CountDownLatch countDownLatch = new CountDownLatch(6);
```

```java
// 業務
countDownLatch.countDown(); // 計數器 -1

countDownLatch.await();     // 等待計數器歸零才向下執行
```

非常簡單，適用於需要先完成某些方法。

每次有執行緒調用`countDown()`數量就會-1，

計數器變成0，

`await()`就會被喚醒繼續執行。

## CyclicBarrier

加法計數器

```java
CyclicBarrier cyclicBarrier = new CyclicBarrier(10,new Runnable());
// 當有十條執行緒在cyclicBarrier.await()，就會執行後面的Runnable();

cyclicBarrier.await();
```

## Semaphore

信號量

可以理解為停車場

```java
Semaphore semaphore = new Semaphore(5);

semaphore.acquire();
semaphore.release();
```

在執行緒中，取得停車位`acquire()`，

如果停車位滿了，其他執行緒就要等待，

停車完的執行緒調用`semaphore.release()`釋放停車位。

# ReadWriteLock

讀寫鎖

獨佔鎖（寫鎖）一次只能被一個線程佔有

共享鎖（讀鎖）多個線程可以同時佔有

```java
ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
// 寫入資料時用writeLock()
readWriteLock.writeLock().lock();
readWriteLock.writeLock().unlock();
// 讀取資料用readLock()
readWriteLock.readLock().lock();
readWriteLock.readLock().lock();
```

# BlockingQueue

先進先出。

與`Set`和`List`一樣，`BlockingQueue`也實作了`Collection`。

什麼情況會用到？多線程併發、線程池

```java
ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3); // 傳入大小
```

## 四組API

| 方式       | 拋出異常 | 回傳值，不拋異常 | 阻塞 等待 | 超時等待 |
| :--------- | -------- | ---------------- | --------- | -------- |
| 添加       | add      | offer            | put       | offer()  |
| 移除       | remove   | poll             | take      | poll()   |
| 判斷對列首 | element  | peek             | -         | -        |

> **超時等待的方法需要傳入時間參數。**

### SynchronousQueue

和其他的BlockingQueue不同，

SynchronousQueue如果`put`了一個元素進去，

必須先`take`出來，

否則不能再放進去。

 ```java
BlockingQueue<String> synchronousQueue = new SynchronousQueue<>();
 ```

# 線程池（重點）

優化資源的使用，更加快速。

```java
ExecutorService threadPool1 = Executors.newSingleThreadExecutor();  // 單個線程
ExecutorService threadPool3 = Executors.newFixedThreadPool(10);     // 創建一個固定大小的線程
ExecutorService threadPool2 = Executors.newCachedThreadPool();      // 動態大小

// 有線程池後就使用它來創建
threadPool1.execute(new Runnable() {
    @Override
    public void run() {
        System.out.println("haha");
    }
});
```

## 七大參數

原始碼分析

```java
public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService
        (new ThreadPoolExecutor(1, 1,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>()));
}
public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>());
}
public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>());
}
```

進入原始碼我們可以發現，

他們都是調用`ThreadPoolExecutor()`，

再次點入`ThreadPoolExecutor()`的原始碼，

```java
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) {
    if (corePoolSize < 0 ||
        maximumPoolSize <= 0 ||
        maximumPoolSize < corePoolSize ||
        keepAliveTime < 0)
        throw new IllegalArgumentException();
    if (workQueue == null || threadFactory == null || handler == null)
        throw new NullPointerException();
    this.corePoolSize = corePoolSize;
    this.maximumPoolSize = maximumPoolSize;
    this.workQueue = workQueue;
    this.keepAliveTime = unit.toNanos(keepAliveTime);
    this.threadFactory = threadFactory;
    this.handler = handler;
}
```

來看看這些參數

```java
public ThreadPoolExecutor(int corePoolSize,  // 核心線程數
                          int maximumPoolSize, // 最大線程數
                          long keepAliveTime,  // 額外線程的閒置存活時間
                          TimeUnit unit,  // 時間單位
                          BlockingQueue<Runnable> workQueue,  // 阻塞隊列
                          ThreadFactory threadFactory,  // 線程工廠
                          RejectedExecutionHandler handler) // 拒絕策略
```

線程池就像一間銀行，核心線程數是平時櫃檯的數量，

當核心線程數達到了，多的線程會去候位區（阻塞隊列）中，

當阻塞隊列也滿了，銀行就會加開櫃檯，

一直到最大線程數，

如果這也滿了，就會發動拒絕策略。

最後客人都走了，

加開櫃檯（超出核心線程數的線程）在超過閒置存活時間後便會關閉。

## 四大拒絕策略

`Interface RejectedExecutionHandler`有四個實現類，

```java
new ThreadPoolExecutor.AbortPolicy()          // 不理人，拋出異常
new ThreadPoolExecutor.CallerRunsPolicy()     // 哪裡來的回哪裡（例如main線程）
new ThreadPoolExecutor.DiscardPolicy()        // 拋棄任務，不會拋出異常
new ThreadPoolExecutor.DiscardOldestPolicy()  // 嘗試和最早的競爭，不會拋出異常
```

## 最大線程該如何決定？

1. CPU密集型

   - 從CPU可以同時跑多少線程來決定，如何動態得知？

   - ```java
     Runtime.getRuntime().availableProcessors() // 返回CPU能同時執行的最大線程數。
     ```

     

2. IO密集型

   - 判斷你的程式中十分耗IO的線程，如果有n個，就是n*2個最大線程。

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

---

## Stream流式計算

把計算都交給`Stream`

```java
List<Integer> list = new ArrayList<>();
```

現在有一個`list`

使用`list.stream()`

這個`strea()`中有很多方法可以對`list`的內容做判斷。

例子：

```java
// 通過filter()得到list中大於2的值
list.stream().filter((i)->i>2).forEach(System.out::println);
```

# ForkJoin

將一個任務拆分成數個小的子任務，

每個子任務都會有結果，

最終將所有結果彙整。

## ForkJoin 特點：工作竊取

這裡面維護的都是雙端隊列。

假如有線程A和線程B，

線程A的工作先做完了，

它會把線程B的工作偷過來執行，

以提高效率。









待更新

