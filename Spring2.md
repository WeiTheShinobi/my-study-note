# Spring

> 筆記作者：葉高緯 Wei the Shinobi

# IOC 流程

container 存儲 bean 物件，用map存

Bean定義：xml、註解 -> 轉換成`BeanDefinition`物件

解析配置文件和解析註解一樣嗎？不

那麼這麼多不同種類的方法都要創建需要什麼？介面

所以`BeanDefinitionReader`介面誕生了

配置文件和註解通過`BeanDefinitionReader`介面生成`BeanDefinition`

現在有了`BeanDefinition`，要如何實例化？

容器的入口：BeanFactory介面

the root interface for accessing a Spring bean container

這裡要講講反射

```java
Class clazz = Class.forName();
Class clazz = 類名.class;
Class clazz = 物件名.getClass();

Constructor ctor = clazz.getDeclaredConstructor();
Object obj = ctor.newInstance();
```

BeanDefinition 經過 BeanFactoryPostProcessor 介面的子類處理後

處理什麼？例如註解解析、配置文件之類的

然後通過反射實例化

初始化：

- 填充屬性 -> populateBean
- 執行 Aware 介面的方法
- before -> BeanPostProcessor
- init-method
- after -> BeanPostProcessor

得到完整物件

Aware 介面是幹嘛的？

bean 可以分為**用戶 bean 物件**和**容器物件**(Environment, ApplicationContext, BeanFactory)

Aware 介面是為了**使某些自定義物件能夠方便獲取容器物件**

只要實現 Aware 介面即可

而 Aware 介面其實就是容器物件的 set 方法。

before 和 after 通過 BeanPostProcessor 介面創建代理物件實現動態代理(jdk 或 cglib)

---

如果想要在不同階段做不同的處理工作怎麼辦？

觀察者模式

---

整理一下流程：

1. 創建 BeanFactory 容器
2. 加載配置文件，解析 bean 定義訊息，包裝成 BeanDefinition
3. 執行 BeanFactoryPostProcessor

準備工作：準備BeanPostProcessor、監聽器、廣播器


4. 實例化
5. 初始化
6. 得到物件

`ApplicationContext.refresh()`

包含了13個子方法

看原始碼的技巧：

1. 先看脈絡在看細節
2. 不要忽略註釋
3. 見名知意
4. 大膽猜測，小心驗證

# AOP

匹配方法：正則表示式

方法的哪些位置可以插入？

| 前面   | Before         |
| ------ | -------------- |
| 後面   | After          |
| 前與後 | Around         |
| 異常   | afterThrowing  |
| 回傳   | afterReturning |

我們還需要 advice

這些就是 AOP 的所有操作

那執行順序呢？

AOP 中的重要概念：責任鏈模式

通知想要執行時，需要進行相關的連結，需要實現 MethodInterceptor 介面

chain 物件通過責任鍊模式調用

chain -> a -> chain -> b -> chain -> c -> ....

