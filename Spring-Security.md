# Spring Security

重量級的安全框架，用戶認證與用戶授權是兩大核心部分。

- 用戶認證是指能否登錄
- 用戶授權是指用戶的權限

## 快速開始

導入依賴後即可使用，

進入網頁會跳轉到登入頁面，

默認使用者為`user`，密碼會在`console`中生成。

# 基本原理

Spring Security本質上就是一個過濾器鍊

就是一大堆的過濾器實作了`Filter`介面

## 加載過程

`DelegatingFilterProxy.class`中的`doFilter`方法

`doFilter`中會再調用`initDelegate`方法

再從`FilterChainProxy`中載入過濾器

## 兩個重要介面

- `UserDetailsService`

查詢使用者和密碼的介面

創建類繼承`UsernamePasswordAuthenticationFilter`重寫三個方法

創建類實現`UserDetailsService`返回`User`物件

- `PasswordEncoder`

加密器

返回`User`物件裡的密碼

# web權限方案

