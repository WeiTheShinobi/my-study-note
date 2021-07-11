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

