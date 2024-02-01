# -*- coding: utf-8 -*-
import time
from datetime import datetime, timedelta

import requests
import yaml
import json

with open("../../application.yml", "r") as f:
    config = yaml.safe_load(f)


api_key=config['ai']['key']['wenxin']['api_key']
secret_key=config['ai']['key']['wenxin']['secret_key']


def getAccessToken():
    with open('key.txt', "r+") as f:
       tokens=f.readlines()
    token_string=None
    if len(tokens)!=0 and tokens is not None:
        token_string=tokens[-1]
        time_str=token_string.split('=')[0]
        token=token_string.split('=')[1].rstrip()
        #计算token是否过期
        if(check_token(time_str)):
            return token
    url = "https://aip.baidubce.com/oauth/2.0/token?client_id=" + api_key + "&client_secret=" + secret_key + "&grant_type=client_credentials"
    payload = json.dumps("")
    headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
    response = requests.request("POST", url, headers=headers, data=payload)
    token=response.json().get("access_token")
    time_str=str(int(time.time()))
    with open('key.txt', "a+") as f:
        f.write(time_str+'='+token+'\n')
    return token

#返回不过期bool
def check_token(timestamp):
    # 将时间戳转换为datetime对象
    t=int(timestamp)
    dt_timestamp = datetime.fromtimestamp(t)
    current_time = datetime.now()
    # 计算时间差
    time_difference = current_time - dt_timestamp
    one_month = timedelta(days=30)
    # 检查时间差是否超过一个月
    return time_difference <= one_month

#默认模式
def common(content):
    json={  "messages": [{"role":"user","content":content}]}
    token = getAccessToken()
    url = 'https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant?access_token=' + token
    res = requests.post(url=url, json=json)
    if(res.status_code==200):
        return res.content.decode("utf-8")
    else:
        raise "wenxin请求出错，状态码"+str(res.status_code)+"  错误:"+res.content.decode("utf-8")

def hello():
    data={  "messages": [{"role":"user","content":"java 效率最高的http请求组件"}]}
    token=getAccessToken()
    url = 'https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant?access_token=' + token
    res = requests.post(url=url, json=data)
    print(res.content.decode('utf-8'))


if __name__ == '__main__':
    print(common("http2与grpc"))
