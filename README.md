## MY安全实训比赛，训练，交流一体化平台。

### 初步构想

几大模块：ctf靶场与比赛、安全团队训练（任务与进度公开）、成员资料展示、比赛动态与交流、学习文章分享

正在施工：ctf靶场与比赛模块的后端

## 进展

完善docker api 能够开启关闭靶机,
完善了用户数据接口的基本功能，设置了密码hash和salt,jwt认证，cookie，Session双重保证
设置了拦截器，有些api,需要登录授权


## 测试 api 文档

### ctf部分
```text
 /ctf           测试，返回json  
 /api/data/docker/all    获取所有存活的靶机
 /api/data/docker/stopAll        停止所有存活靶机
 /api/data/docker/start
 get 请求 
 参数： username: 用户名(现在是根据用户名创建用户对象）   path:启动靶机的路径（根据配置文件，如flask/ssti）
 测试阶段，
 /api/ctf/docker/stop
 get请求
 参数： username:根据用户名关闭靶机
```
### 用户部分

其中还未开启密码hash存储，cookie校验和重定向
有些接口需要cookie中的jwt,jwt登录接口获取

- /api/data/user/login/用户名/密码        登录 
- /api/data/user/register               注册
 post json参数
```json
    {
    "username":"用户名" ,"password":"密码","email":"邮箱",
     "inner":"是否为校内成员","manager":"是否为管理员",
    "studentInfo(可选)":"如下对象"
    }
```
其中studentInfo对象：
```json
    {
    "realName":"真实姓名","age":"年级","department":"学院",
     "direction":"方向","major":"专业","qq":"qq号"
    }
```
- /api/data/user/destory  注销
- /api/data/user/update/类型/值     修改
类型：username,password,email,manager
- /api/data/user/query/用户名   查询

### sql测试
创建数据库的文件：/src/main/resource/mysec.sql
- /test/sql
- /test/obj