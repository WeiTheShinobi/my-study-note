# Docker

為什麼會有 docker？

docker 是一個虛擬機器

統一環境，避免不同機器上不能執行

每次用不同電腦都要裝一堆東西不累嗎

一次打包鏡像

就像 Android 的應用程式執行在虛擬環境

我們下載 docker 鏡像就可以用了

還可以彼此隔離。

在容器技術前，都是使用虛擬機技術

虛擬機很笨重

優點：

- 一鍵打包
- 簡單擴展
- 簡單運維
- 更高校的資源利用

## Docker 組成

鏡像( image )：模板，透過鏡像可以創建多個容器

容器( container )：Docker 通過容器技術，運行應用，透過鏡像創建

可以把容器理解為簡易的 Linux 系統

倉庫( repository ) ：存放鏡像的地方

## 開始

```shell
docker run hello-world
```

尋找本地鏡像 -> 找到就運行 -> 找不到就去docker hub下載 -> 執行 

## 命令

```shell
# 遇事不決 --help
docker images --help
docker rmi -f 關鍵字  # remove image

docker search 搜索鏡像
--filter=STARS=3000 # 過濾星星數三千以上

# 搜尋到就可以用
docker pull mysql[: 版本]

docker run [參數] image
--name="name"
-d # 後台運行
-p ip:主機端口：容器端口
-p 主機端口：容器端口
-p 容器端口
-p   # 隨機指定端口

docker ps # 列出運行中的容器
```

