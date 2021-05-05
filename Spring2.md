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
