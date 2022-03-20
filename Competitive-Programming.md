## Competitive Programming

> 筆記：Wei the Shinobi

### 訓練

- 多參加比賽、模擬
- 思考邊界條件
- 先計畫再動手，不能靠蠻力
- 結束後復盤，重新思考如何思考？如何歸納？
- 比賽中保持住心態

### 技巧

- 起點有多個，終點只有一個時，可以改從終點開始找
- 有時思考的方向一開始就錯了，一直想不到時要換換出發點
- `StringBuilder.toString()`是一個花費巨大的操作

### Graph Theory

##### 二分圖

- 二分圖只有雙數環，若探測到單數環就不是二分圖
- 替點標記顏色兩種，若相鄰點顏色相同就不是二分圖

#### DFS

- 白灰黑三色可以探測有環

#### BFS

- 計算最短的距離、次數、階層
- **拓撲排序**是 BFS 的一種變體

##### Topological Sort

1. 尋找 in-degree 為 0 的點（只出不進的點）加入 queue 中
2. 持續`queue.poll()`刪除並更新其他點的 in-degree
3. 重複以上步驟直到 queue 為空

#### 單條最短路徑

> 圖上無負環時是 P 問題，反之則是 NP-complete 問題

##### Dijkstra

```java
int[] 到起點的最短路徑;
int[] 前一個點;
```

貪心

選定一個起點

尋找不再最短路徑上且離起點最近的點

新點路徑長 + 前一個到起點的距離 = 新點到起點距離

直到所有點都被加入

##### Bellman

重複 n - 1 輪鬆弛



#### 最小生成樹

> 用貪婪的方式

##### Kruskal

由小到大排序路徑權重( heap )，

`heap.poll()`新增 edge，如果該 edge 會產生**環**則跳過 ( 沒有新的點 )

直到 edge 的數量到達 n - 1

##### Prim

與 Kruskal 差不多

但我們要尋找離樹最近的點，

`heap.poll()`新增 edge，如果該點已在圖中則跳過

直到所有的點都在圖中 ( set )

#### Union Find - DIsjoint Set

