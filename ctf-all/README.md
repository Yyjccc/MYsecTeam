# ctf模块


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

### ctf题目
targetDrone对象:

```json
{
 "id": "id（long）",
 "name": "题目名字",
 "description": "题目描述",
 "attachment": "是否存在附件(bool)",
 "docker": "是否存在靶机(bool)",
 "hints": "提示",
 "level": "难度等级（int）",
 "wp": "wp地址",
 "solvedUser": "解出的用户（jsonStr）",
 "category": "题目所属的方向",
 "labels": "题目标签（jsonStr）",
 "provider": "提供者的id",
 "source": "来源",
 "date": "上传日期"
}
```

1. 获取全部题目:
   /api/data/targetDrone/getAll

2. 获取某个方向的题目
   /api/data/targetDrone/get/方向(web、reverse、pwn、crypto、 misc、other)

3. 增加题目
   /api/data/targetDrone/add
   POST targetDrone对象json数据,返回题目id,其中bool类型数据由系统控制,name和category必须参数

4. 修改题目数据
   /api/data/targetDrone/update/{id}/字段名/值

5. 删除题目数据
   /api/data/targetDrone/delete/{id}


### 文件上传
1. 上传靶机压缩包
   /api/upload/docker/{id}
   上传file,只允许zip,tar,tar.gz类型的压缩包

2. 上传附件压缩包
   /api/upload/attachment/{id}
   上传file

## 接口调用相关说明
上传题目的调用接口完整流程：
1. 添加题目数据，拿到id
2. 若题目有附件，根据id调用上传附件的接口
3. 若题目存在靶机，根据id调用上传靶机压缩包的接口




### sql测试
创建数据库的文件：/src/main/resource/mysec.sql
- /test/sql
- /test/obj



# 未完成的接口

# CTF 题库
### 管理员
- 管理员修改全局配置
- 管理员上传和删除题目和附件
- 重构用户开启靶机和题目
- 设置题目计时，和靶机自动关闭
- 管理员修改题目的配置文件


### 题目数据
- 题目分发和附件下载
- 题目搜索功能
- wp

### 其他
- 提供各种ai模型的api


### ctf用户
- 产出积分统计

