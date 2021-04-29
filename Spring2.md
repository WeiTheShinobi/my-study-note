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

