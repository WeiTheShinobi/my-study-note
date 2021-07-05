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

