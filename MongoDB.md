# MongoDB

MongoDB是一種開源的NoSQL，用集合和文檔替代表和行。

數據類型BSON

# 快速開始

```sh
mongod --dpath=..\data\db
```

開啟資料庫

```shell
mongo --host=127.0.0.1
```

連接

`show dbs`查看資料庫

`use 資料庫` 使用

`db.dropDatabase()`刪除

集合可以顯式或隱式創建

可以使用`try/catch`

MongoDB的命令都是

```shell
db.comment.命令
```

舉例：

更新

```shell
db.comment.update(query,update,options)
```

