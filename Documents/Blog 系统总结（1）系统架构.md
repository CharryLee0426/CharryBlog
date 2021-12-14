## Blog 系统总结（1）系统架构

### 0. 技术栈

前端：

* Semantic-UI 组件库。（需要引用 jQuery）
* editormd 网页 markdown 编辑器
* prism 代码高亮插件
* Qrcode 生成二维码插件 
* tocbot 目录生成插件
* thymeleaf 模版

后端：

* Spring Boot
* JPA
* LogPack 日志

### 1. 项目结构

在主程序目录下：

* com.lichenxidian.blog  业务逻辑
  * po  实体类
  * dao  数据持久层（负责操作数据库）
  * service  服务层（负责定义实体具体的 CRUD）
  * web  网络层（负责具体的业务实现）
  * interceptor  拦截层（负责拦截非法登录）
  * aspect  切面层（测试 Spring boot 的 AOP 特性，实际项目中没有用到）
  * handler  调用层（一个异常的调用）
  *  vo  视图对象层
  * util  工具类（负责处理一些普遍的工具业务）

* resources  资源
  * static  静态资源（比如贴图，css，js，不通过 CDN 使用的库等）
  * templates  模版（也就是 Spring Boot 项目要显示的网页，里面是一些 .html 文件）



具体来看：后端的逻辑简单说来就是定义数据操纵的方法。下面是一个自上而下的分析。

> web			具体业务实现，比如数据展示，数据更新等
>
> service		服务层，这里只定义增删改查的具体实现
>
> dao			这层可以用 SQL 语句和 JPA/MyBatis 等持久层框架将 Java Bean 和 POJO 映射到数据库上简单的操作数据库
>
> po				定义数据库中的视图，如果使用 JPA 的话可以通过注解的方式运行一次项目后自动建表



而前端的话可以分为作为首页部分对外展示的前台部分和作为 admin 管理、发布博客的后台部分，两者有一些部分是比较相近的。

* 前台

  * index  首页
  * blog  博客文章展示页
  * about  关于页，个人信息
  * archives  归档页
  * types  分类页
  * tags  标签页
  * search  搜索结果页（在执行搜索后展示搜索结果）
  * _fragments  存一些 thymeleaf 使用的一些模版

* 后台

  * login  登录页面

  * index  登陆后管理后台首页
  * tags  标签管理页
  * tag-input  新增/编辑标签页
  * types  分类管理页
  * type-input  新增/编辑分类页
  * blogs  文章管理页
  * blog-input  新增/编辑文章页
  * _fragments 存一些 thymeleaf 使用的一些模版