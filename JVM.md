# JVM

> 筆記作者：葉高緯 Wei the Shinobi
>
> 參考資料：[【狂神说Java】JVM快速入门篇](https://www.bilibili.com/video/BV1iJ411d7jS)

# JVM 結構

<img src="./image/jvm/jvm01.png">

# 加載器與雙親委派

<img src="./image/jvm/jvm02.png">

加載器：


- 根加載器
- 擴展類加載器
- 應用程式加載器

雙親委派：

- 先找根加載器，如果沒有
- 找擴展類加載器，如果沒有
- 找應用程式加載器（我們寫的程式）
- 如果都沒有，ClassNotFoundException







# 待更新

