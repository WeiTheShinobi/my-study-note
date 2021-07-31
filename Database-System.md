# Database System

> 筆記：葉高緯 Wei the Shinobi
>
> 學習資料：[吳尚鴻資料庫系統概論](https://www.youtube.com/watch?v=h2-S2B9tRk0&list=PLS0SUwlYe8cyln89Srqmmlw42CiCBT6Zn&ab_channel=NTHUOCW)

## Java 多線程

在 Java 中，物件會放在 heap 中，heap是所有 threads 共享的

這代表兩個 thread 可以存取同一個 object

在寫 database code 的時候要對多線程非常了解

畢竟都在操作同一份資料

- 並不是把方法都寫上 synchronized 就能避免 concurrency issue

重點是你要 synchronized 在對的地方

例如包覆整個完整的步驟

- 在 call `wait()`時，通常會放在 loop 中。**不要使用`if`代替 loop** 

## Chapter 2, 3

- `DBMS != Database`

database 指的是存在 DBMS 中的資料，

DBMS(DataBase Management System) 是管理 databases 的軟體。

- 常見的 data model：ER model 與 relational model

- ER model  = Entity Relationship model

Enitity：object

Relationship：has-a，可能是1-1、1-many、many-many

- 思考好 ER model 後轉換成 relational model

你可以把 relational model 想成 tabel model

將 enitity 轉成 tabel

會有一個 primary key

Foreign Key：指向別的 table，只出現在 1-1 或 1-many

Schema：一個 database 的結構

## Chapter 4, 5

- How is a query answered?

```mysql
SELECT b.blog_id
	FROM blog_pages b, users u
	WHERE b.author_id = u.user_id
		AND u.name = 'dog'
		AND b.created >= 2011/1/1;
```

- `FROM blog_pages b, users u`

`p = product(b,u)`：先試著把兩個 table 合併成一個大的 table p

- `WHERE b.author_id = u.user_id
  		AND u.name = 'dog'
    		AND b.created >= 2011/1/1;`

`s = select(p, where...)`：根據條件查詢，得到 table s

- `SELECT b.blog_id`

`project(s, select...)`：從 table s 挑出 column b.blog_id

- query algebra 在運行時就是輸入 table，輸出 table。

tree 並不一定是照上面這個順序，database 會尋找最好的方法。

## Chapter 19

如何把資料存得比較好？

- Normalization

將一個大表拆分來避免 anomaly

- 符合 3rd normal form

## Architecture and Interfaces

- SQL
- JDBC interface

透過 driver 取得 connection，

execute，

使用完 close connection，

不然會占用 cache，或是 lock 資料。

- native interface

在伺服器端執行

類似JDBC

- storage interface ：block 是讀取的最小單位，存進 page 中
  - RecordFile
  - metedata

  一些使用者不需要知道，但系統需要知道的資料
  
- How are the database/tables/records stored in a file ststem?
  - Database: directory
  - Table: file
  - Record: bytes

## Server and Threads

在講 Engine 前，需要先了解 system 會怎麼去 run request

### Process vs. Thread

Thread：a unit of CPU execution + local resources

Process：threads (at least one) + grobal resourses

- 如何 mapping user thread 和 kernal thread？
  - many-to-one
  - one-to-one (主流：避免 blocking problem)
  - many-to-many (動態的選擇 many-to-one 或 one-to-one)

- how about java threads?

取決於 JVM，通常是 one-to-one

還有一個好處是 system independent (if there's a JVM)

### Supporting concurrent clients

- 同時執行還是單一執行？

同時執行可以更快，也避免浪費運算資源

- 在 threads 中共享資源

可以使用 **static** 或是**傳入參數**來共享。

### Embedded client

操作本地端的資料庫

通常是單執行續

但就算在本地端還是用 remote client 的方式

不用 native interface

remote 也不一定要真的 remote

### Remote clients

client 傳送 request 給 server，

server 創建 worker thread 執行請求。

- What is request?

需要設計一個 request 要怎樣的規模

一個 I/O 還是 conn ?

以 VanillaDB 是 connection

conn 可以確保順序

- thread pool
  - 預先創建好 worker thread，收到 request 後再挑一個執行，其他的先睡覺
  - grace performance degradation：user 太多會讓 server 品質下降，這可以讓後來的 user 不影響先來的 user 的品質

## Query Processing

native interface 中會讓 planner 來執行

- What does a Planner do?

1. 解析 SQL
2. 驗證 SQL
3. 尋找合適的 plan 給 SQL
4. 看 SQL 是 query 還是 update，回傳查詢結果或是受影響的資料數

- Lexer, parser, SQL data

解析

- Predicates
- Verifier

語法對不一定可以執行，所以需要額外驗證

- plan and scan

解析完尋找最好的 plan

- Operators

實作 operator 讓 plan 去尋找執行

scan 代表一個 operator 的 output

可以設計 scan tree，

每個 scan output 都可以在傳入下個 sacn

## Data Access & File Management

- Data Access Layer
- Disk access

為什麼要存在 Disk？持久化

在 Disk 需要 I/O 操作，比較慢但 Disk 價格便宜

HDDs 比 RAMs 慢十萬倍

SSDs 比 RAMs 慢一千倍

Disk **連續資料**比**隨機資料**快

SSDs 不管讀取大小，最小單位都是 block

- OS 提供兩個 disk access APIs

1. block-level 

2. file-level

比較高階

- Contuinuous Allocation：把資料連續放
  - internal fragmentation：資料小於 block 造成空間浪費
  - external fragmentaion：連續的資料太長，雖然有很多空間，但所有空間都短於資料導致無處存放

- Indexed Allocation：一個 index block 來記憶 logical block 的地址，取出的數值叫 physical block
- when **seek** is called

byte position -> logical block -> physical block -> sectors

### File Management in VanvillaCore

- 希望存取速度越快越好

- Block-Level Based

好處：

1. 完全控制資料的物理位置，決定放置位置可以更快
2. 避免 OS 的限制

缺點：

1. 完全控制代表實作複雜
2. "Raw disk" 通常是每個 OS 特別的，導致傷害了跨平台的能力

- File-Level Based

好處：簡單方便

壞處：

1. 無法完全控制
2. 某些實作傷害了**正確性**，可能優化作了某些不想要的事
3. 承2，DBMS必須自行控制 flush 來確保 ACID

#### VanvillaCore's Choice

file-level，但存取直接 logical block

好處：簡單、能管理 flush、能管理 block 裡面

壞處：總是需要預設硬碟是**隨機存取**，包括連續資料

這種方法也被很多 DBMS 採用

- Files

1. 一個檔案一個 table 和 index。 E.g., xxx.tbl
2. Log files。 E.g., vanilladb.log

## Memory Management

為了減少 I/O

使用一些 cache 就可以大幅加速

- 為什麼不用 OS 提供的 virtual memory?

1. 讓 OS 控制 cache 缺乏操控性，可能做出我們不想要的事情
2. 不能控制 delayed writes，在 DBMS 中我們需要精確控制

## Transaction

- Concurrency manager for C and I

釋放 locks

- Recovery manager for A and D

flush 後會有 log，讓你可以 rollback

- transaction manager：管理 transaction
- lifecycle

1. Begin
2. End statement
3. Commit or rollbacl

