# Lichen_Blog

#### 介绍
一个具有后台管理页面，可在线上传文章的博客系统。

#### 软件架构

标准的 SpringBoot 项目。


#### 安装教程

1. git clone
2. 打开文件，通过 maven 配置依赖
3. 修改 `/src/application.yml` 中的配置选项，具体来说就是数据库的用户和密码。
4. 在数据库中创建 blog 数据库
5. 从 BlogApplication 类开始构建并运行项目，
你可以通过 `localhost:8080/` 访问（如果你没有修改服务器端口号的话）。

#### 使用说明

1. 运行在 8080 端口上
2. 需要在数据库中的 t_user 表创建用户记录作为管理员。
3. 需要将 t_comment, t_blog 中的 content 属性修改为 longtext，否则只能存储 255 字符的内容。
4. 上传图片需要将图片的 url 上传，文内图片支持 markdown 语法，仅支持通过 url 上传，这里推荐聚合图床 [http://www.superbed.cn](http://www.superbed.cn)

#### 参与贡献

1.  Fork 本仓库
2.  新建分支
3.  提交代码
4.  新建 Pull Request