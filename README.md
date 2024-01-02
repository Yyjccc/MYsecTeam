## MY安全实训比赛，训练，交流一体化平台。

### 初步构想

几大模块：ctf靶场与比赛、安全团队训练（任务与进度公开）、成员资料展示、比赛动态与交流、学习文章分享


正在施工：ctf靶场与比赛模块的后端

## 进展

完善docker api 能够开启关闭靶机

用户层还未开始，所以暂时为测试接口，参数可能后面会修改


## 测试 api 文档
    - /ctf
测试，返回json  
    - /ctf/api/docker/all 
获取所有存活的靶机
    - /ctf/api/docker/stopAll
停止所有存活靶机
    - /ctf/docker/start
get 请求 
参数： username: 用户名(现在是根据用户名创建用户对象）   path:启动靶机的路径（根据配置文件，如flask/ssti）
测试阶段，

    - /ctf/docker/stop
get请求
参数： username:根据用户名关闭靶机


