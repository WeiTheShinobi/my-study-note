# Tomcat

> 筆記作者：Wei the Shinobi 葉高緯
>
> 關於 Tomcat 的深入學習

## 什麼是Servlet

server + applet = servlet

Servlet 是一個規範

遵守這個規範

讓諸如Tomcat、JBoss之類的東西去實現Servlet

Servlet 就像一個小程式

它也被其他東西包裹著

結構：Servlet -> Wrapper -> Context -> Host -> Engine

在配置文件中定義節點會照著這些架構

## 容器機制

tomcat 從配置文件找到 servlet 位置

tomcat 生成 RequestFacade 和 ResponseFacade

使用 servlet 的 doGet, doPost

### 流程

在執行Servlet前

先經過 filterChain

`filterChain.doFilter(request.getRequest(), response.getResponse())`

Request 和 Response 使用了 Facade Pattern

所以`request.getRequest(), response.getResponse()`會拿到 Facade

然後 tomcat 只會調用`Servlet.service(request, response)`

在`service()`中再判斷該做 get 還是 post

簡圖：

Endpoint透過Socket獲取數據 -> 解析數據 -> Requset -> 容器[Engine-Pipeline -> Host-Pipeline -> Context-Pipeline -> Wrapper-Pipeline -> FilterChain -> Servlet]

## HTTP、TCP

HTTP協議是瀏覽器與伺服器之間的數據傳輸協議，(瀏覽器、tomcat)

基於TCP/IP協議。(作業系統實現)

介面 Socket 

socket 方法會一層一層調用到作業系統的方法

但 windows 看不到，因為不開源

在 Linux 中，萬物皆 file

socket 用 IO流派傳輸數據

然後解析 HTTP

