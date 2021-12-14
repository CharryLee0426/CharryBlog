## Blog 系统总结（4）前端数据获取

### 1. 适用模版

本项目不是传统意义上的完全前后端分离项目，后端处理完数据后，会将数据传给 model。通过 thymeleaf 模版，可以做到既可以静态网页预览，也可以在项目启动后由后端动态控制网页。这节总结一下 thymeleaf 常用的内联样式。

### 2. 模版（片段）

在 实际开发过程中，不同页面中的一些部分，比如 css/js 引入、导航栏、底部 footer等部分都有可能会多次使用，这时候就可以通过片段功能做一些动态效果或简单的引入。请看如下片段。

> _fragments.html

```html
<head th:fragment="head(title)">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title th:replace="${title}">博客详情页</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css">
    <link rel="stylesheet" href="../static/css/animate.css" th:href="@{/css/animate.css}">
    <link rel="stylesheet" href="../static/css/me.css" th:href="@{/css/me.css}">
    <link rel="stylesheet" href="../static/css/typo.css" th:href="@{/css/typo.css}">
    <link rel="stylesheet" href="../static/lib/prism/prism.css" th:href="@{/lib/prism/prism.css}">
    <link rel="stylesheet" href="../static/lib/tocbot/tocbot.css" th:href="@{/lib/tocbot/tocbot.css}">
</head>
```

上面的片段作用是在页面中引用需要用到的 css 文件，其中 `th:fragment` 即为 thymeleaf 指令，作用为定义其为一个片段，可以被其他网页引用。如下就是引用的方法。

```html
<head th:replace="_fragments :: head(~{::title})">
```

其中 `th:replace`就是选取模版替代的命令，而选取路径不必加 `.html` 后缀，两个冒号以后可以选取片段，`~{::xxx}` 可以选中 xxx 标签内的内容。

模版中也可以加入变量和选择控制条件，动态选择内容。比如下面的片段。

```html
<nav th:fragment="menu(n)" class="ui inverted attached segment m-padded-tb-mini m-shadow-small" >
        <div class="ui container">
            <div class="ui inverted secondary stackable menu">
                <h2 class="ui teal header item">Blog</h2>
                <a href="#" class="m-item item m-mobile-hide" th:href="@{/}" th:classappend="${n==1} ? 'active'"><i class="home icon"></i>首页</a>
                <a href="#" class="m-item item m-mobile-hide" th:href="@{/types/-1}" th:classappend="${n==2} ? 'active'"><i class="idea icon"></i>分类</a>
                <a href="#" class="m-item item m-mobile-hide" th:href="@{/tags/-1}" th:classappend="${n==3} ? 'active'"><i class="tags icon"></i>标签</a>
                <a href="#" class="m-item item m-mobile-hide" th:href="@{/archives}" th:classappend="${n==4} ? 'active'"><i class="clone icon"></i>归档</a>
                <a href="#" class="m-item item m-mobile-hide" th:href="@{/about}" th:classappend="${n==5} ? 'active'"><i class="info icon"></i>关于我</a>
                <div class="right item m-mobile-hide">
                    <form action="#" name="search" target="_blank" method="post" th:action="@{/search}">
                        <div class="ui icon transparent inverted input">
                            <input type="text" name="query" placeholder="Search..." th:value="${query}">
                            <i onclick="document.forms['search'].submit()" class="search link icon"></i>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <a href="#" class="ui menu toggle black button m-right-top m-mobile-show"><i class="sidebar icon"></i></a>
    </nav>
```

其中，`th:href` 可以设置 href 到后端的 request，`th:classappend` 可以在静态页面中加入 class 属性值，这里使用了一些三目运算。`th:xx="xxx"` 指令就是动态设置 xx 属性，`@{xxx}`可以定位到文件目录或者 request url属性，`${xx}` 可以选到 xx 对象。

如果一个对象用到很多次的话，也可以在这些使用该对象的父级标签上通过 `th:object="${xxx}"` 选取到该对象，然后通过 `*{attr_name}` 选取到对应的属性。

### 3. 常见 thymeleaf 命令

| 命令                               | 作用                                               |
| ---------------------------------- | -------------------------------------------------- |
| th:if="True"                       | 通过 if 判断                                       |
| th:each = "blog : ${page.content}" | 循环便利一个集合或数组中的元素                     |
| th:text=""                         | 将内容转译为标签下的内容（文本方式体现）           |
| th:utext="${blog.content}"         | 将内容直接放在标签下（如果有 html 标签的话会生效） |

### 4. 补充内容

thymeleaf 的模版命令很多，但是大部分都是些细碎的点，平时开发可以时常查看文档。多次使用以后就可以相当熟练的上手了。

下面贴一个博客园（算是小一号的 CSDN）的帖子，搜索基本使用的时候可以查看，虽说这篇总结的也不是很全

点此查看[](https://www.cnblogs.com/msi-chen/p/10974009.html)

