# Git Basic Tutorial

> 这个教程用于介绍如何使用 git 进行版本控制和团队协作。
>
> Author: LC
>
> Date: 2021-04-15



---



### 1. 下载 git 并配置好环境变量

这一步应该没什么好说的



### 2. 注册 gitee 账号

gitee 是中国的 git 代码托管平台，网速比较稳定。注意：**要把邮箱绑定了，之后有用**



### 3. 配置 git 信息
在命令行中输入以下命令可以查看 config 变量

```
git config -l
```


在命令行下输入如下命令

```
git config --global user.name "yourname"
git config --global user.email "youremail"
```

这里的 emai 就是第 2 步中绑定的邮箱。



### 4. Fork 本仓库

在 web 端到本仓库首页，可以看到 fork 按钮，此时就可以将本仓库 fork 到自己的账户上。fork 这个概念相当于把本仓库 copy 到自己的账号下。



### 5. clone 第 4 步 fork后的仓库到本地



### 6. 配置上游URL

输入命令

```
git remote add upstream URL
```

因为自己提交代码最后要提交到 「上游」，也就是被 fork 的那个仓库，所以需要进行如下配置。

这个 URL 可以在原仓库的地址栏处找到。

输入命令

```
git remote -v
```

可以查看 origin 和 upstream 是否配置成功。



### 7. 开始工作

**开始工作前首先要保证本地和 master 分支保持和最新同步**

输入命令

```
git pull --rebase upstream master
```

即可保持同步



**注意，不要在 master 分支进行修改**

因为我们本地的 master 分支要和上游 master 分支保持同步和通信，直接修改master 分支容易发生冲突，所以我们要创建一个新的分支。

输入命令

```
git checkout -b yourbranchname
```

创建新分支，分支名尽量有意义，虽然可以随便起。



### 8. 开始本地文件的修改

正式的工作内容，比如代码，写文档。



### 9. 提交 PR

输入命令

```
git status
```

可以查询更改状态。比如新建文件或文件夹以后刚开始会显示 untracked （不能追踪到）。删除文件或文本文档（也就是代码的变动都可以看出来）。

输入命令

```
git add .
```

将变动添加到暂存区（可以理解为缓存）。提交分为两个部分：1. 本地到暂存区；2. 暂存区到远程仓库分支。add 命令后的「 . 」可以理解为通配符，即将所有的变化统一提交到暂存区。

输入命令

```
git commit -s -m "yourcommentforthiscommitment"
```

`-s` 可以将 name 和 email 附在提交中，方便追踪管理，最后的字符串可以简要描述此次提交的目的，解决的问题。

输入命令

```
git push -u origin yourbranch 
```

将你的 commit 提交到 fork 仓库的非 master 分支（也就是之前你创建的分支）

提交工作完成！



### 10. Compare & pull request

如果前 9 步都没有问题的话，此时你的仓库会受到你提交的信息。此时进入 web 端进行操作。点击分支会收到刚刚提交的分支。然后进行比对，检查无误后就可以点击 **pull request** 进行提交了。我们一般提交到上游的 master 分支。



### 11. 删除分支

```
git checkout branchname
```

切换分支

```
git branch -a
```

列出所有分支

```
git checkout -d branchname
```

删除分支

```
git push origin --delete branchname
```

删除远程仓库的分支