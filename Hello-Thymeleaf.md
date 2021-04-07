# Hello-Thymeleaf

快速了解Thymeleaf

# 基本操作

## 渲染

```html
<h1 th:text="${user}"></h1>
<h1 th:text="${user.name}"></h1>
```

渲染物件與物件內的變數

```html
<div th:object=${user}>
    <h1 th:text="*{name}"></h1>
</div>
```

這種寫法也是可以的

## 布林

```html
<h1 th:if="${user.isVip}"></h1>
```

## each

```html
<ul>
    <li th:each=tag:${user.tags} th:text="${tag}"></li>
</ul>
```

## JavaScript

```javascript
<script th:inline="javascript">
    const user = /*[[$user]]*/{};
</script>
```

# fragment

```html
<div th:fragment="模塊名">
    
</div>
```

```html
<div th:replace="~{文件名::模塊名}">
    
</div>
<div th:insert="~{文件名::模塊名}">
    
</div>
```

使用模塊替換，達到重複使用。

`replace`

`insert`會把外層的標籤也包進去

```html
<div id="模塊名">
    
</div>

<div th:insert="~{文件名::#模塊名}">
    
</div>
```

使用`id`引入

## 傳遞數據

```html
<div th:fragment="模塊名(message)">
    <p th:text="${message}"></p>
</div>
```

```html
<div th:insert="~{文件名::模塊名("傳遞數據")}">
    
</div>
```

## 替換局部

```html
<div th:fragment="模塊名(message)">
    <p th:text="${message}">原本</p>
</div>
```

```html
<div th:insert="~{文件名::模塊名(~{::#message})}">
    <p id="message">替換</p>
</div>
```

