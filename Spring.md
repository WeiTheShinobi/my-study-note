# Spring

> 筆記作者：葉高緯 Wei the Shinobi

輕量級的JavaEE開源框架

Spring的原始碼是優秀的學習範例

Spring的兩個核心：IOC和AOP

- IOC：控制反轉，把創建物件交給Spring管理。
- AOP：切面導向，透過動態代理不修改原始碼進行功能增強。

# IOC

## IOC底層原理

控制反轉(Inversion of Control)：把物件創建和調用的過程交給Spring管理。

目的：降低耦合性

底層原理：

- xml解析
- 工廠模式
- 反射

---

原始方法要調用直接`new`，

但耦合度太高，修改困難，

後來使用**工廠模式**解耦合，

### IOC過程

1. XML配置文件

```xml
<bean id="dao" class="com.dao"></bean>
```

2. 創建工廠類

```java
class UserFactory {
    public static UserDao getDao() {
        String classValue = class屬性值; // XML解析
        Class clazz = Class.forName(classValue); // 通過反射創建物件
        return (UserDao)clazz.newInstance();
    }
}
```

### IOC介面

IOC思想基於IOC容器完成，IOC容器底層就是工廠。

Spring提供IOC容器實現兩種方式（兩個介面）：

- BeanFactory：IOC容器基本實現，Spring內部介面，不是給開發人員用的。
  - 加載配置文件的時候不會創建物件，獲取時才創建。
- ApplicationContext：BeanFactory介面的子介面，提供更多強大的功能，通常用這個。
  - 加載配置文件時即創建物件。

把費時的創建物件過程在伺服器開啟時弄好，提升效率。

`ApplicationContext`的實現類：

- `FileSystemXmlApplicationContext`
- `ClassPathXmlApplicationContext`

## IOC操作Bean管理

- 什麼是Bean管理？

指的是兩個步驟：

- 創建物件
- 注入屬性

Bean管理有兩種操作方式：

- xml配置文件
- 註解

### xml創建

在spring配置文件中，使用bean標籤創建物件。

常用屬性：

- id 唯一標示
- class 類別全路徑

創建物件時，默認無參數建構式。

因為反射創建`newInstance()`沒有參數。

### xml注入

- DI：依賴注入，就是注入屬性

set方法注入：

```xml
<bean id="..." class="...">
    <property name="..." value="..."></property>
</bean>
```

建構式注入：

```xml
<bean id="..." class="...">
    <constructor-arg name="..." value="..."></constructor-arg>
</bean>
```

