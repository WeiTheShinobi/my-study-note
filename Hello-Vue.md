# Hello-Vue

# 快速開始

```html
<div id="app">
    {{message}}
</div>


<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script>
    var vm = new Vue({
        el:"#app",
        data:{
            message:"hello,vue"
        }
    });
</script>
```

導入Vue，與前端數據雙向綁定。

# 簡單的語法

```html
<h1 v-if></h1>
<h1 v-else-if></h1>
<h1 v-else></h1>
```

嘗試遍歷`items`

```javascript
var vm = new Vue({
    el:"#app",
    data:{
        items: [
            {message : 'dog'},
            {message : 'cat'}
        ]
    }
});
```

```html
<div id="app">
    <li v-for="item in items">
        {{item.message}}
    </li>
</div>
```

## 方法綁定

```html
<div id="app">
	<button v-on:click="sayHi"></button>
</div>
```

# 雙向綁定

```html
<textarea v-model="message"></textarea>{{message}}
```

# Vue組件

實現模塊化，讓你的網頁能重複使用。

```html
<body>

<div id="app">
<!--    組件不能直接接收數據，要經過props-->
    <monkey v-for="item in items" v-bind:haha="item"></monkey>
</div>

<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script>
    Vue.component("monkey",{
        props: ['haha'],
        template: '<li>{{haha}}</li>'
    });

    var vm = new Vue({
        el: "#app",
        data:{
            items:["dog","cat","monkey"]
        }
    });

</script>
</body>
```

# 傳遞

- 父傳子 props
- 子傳父 $emit

```javascript
Vue.component("monkey",{
    props: ['index'],
    template: '<li>{{index}}</li>',
    methods: {
        remove: function () {
            this.$emit('remove',index)
        }
    }
});
```

# axios

```javascript
axios.get(地址?查詢字符).then(function(response){},function(err){})
axios.post(地址,物件).then(function(response){},function(err){})
```

