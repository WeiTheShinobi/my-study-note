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

刪除

```shell
db.comment.remove({_id:"1"})
```

刪除`id`為`1`

```shell
db.comment.remove({})
```

刪庫跑路

## 分頁

`db.comment.find().limit(數字)`

`db.comment.find().limit(數字).skip(數字)`

`.sort({KEY:1})`排序查詢

## 正規表示式

```shell
db.comment.find({content:/猴子/})
```

查詢內容含有猴子的文檔

```shell
db.集合名.find({vaule:{$gt:NumberInt(700)}})
```

數值大於700

`$in`：包含

`$and:[ {},{}]`

`$or:[ {},{}]`

# 索引

索引是為了提高執行效率，

所以沒有索引就只能全部掃描了。

MongoDB使用B-Tree的資料結構

## 索引查看

`db.集合名.getIndexes()`

## 創建

`db.集合名.createIndexes(keys, options)`

`db.comment.createIndexes({userid:1})`

後面的參數決定升序(1)或降序(-1)	

## 刪除

`db.集合名.dropIndex(index)`

# 索引使用

## 執行計畫

通過執行計畫查看效率是否提升，

索引是否有效。

只要加上`.explain()`

## 涵蓋查詢

只從索引查詢，不用從集合掃描查詢，高效率。

類似MySQL的覆蓋索引。

# 小實作

導入依賴與編寫配置文件

使用Spring Data的框架

實體類：

```java
// 可以省略 默認使用class名小寫
@Document(collection="表名")
public class Comment implements Serializable {
    // 如果屬性名就叫id 也可以不寫
    @Id
    private String id;
    // 如果屬性名與資料庫不同 在這裡寫上可以映射
    @Field("屬性名")
    private String content;
    
    // 索引
    @ConpoundIndex()
    @Indexes
}
```

創建dao層繼承`MongoRepository<pojo, id>`

## 更新檔案

Service層中的`mongoTemplate`

```java
private MongoTemplate mongoTemplate;

void test(String id) {
    // 查詢條件
    Query query = Query.query(Criteria.where("_id").is(id));
    // 更新條件
    Update update = new Update();
    // 讚數提升1
    update.inc("likenum");
    
    mongoTemplate.updateFirst(query,update,Comment.class)
}
```

# Mongo集群和安全

## Replica Sets 副本集

副本集是一組維護相同數據集的mongo服務。

副本集有兩種類型三種角色

- 主節點
- 副節點

- 主要成員
- 副本成員
- 仲裁者：不保留數據，只投票

