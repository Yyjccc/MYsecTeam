# -*- coding: utf-8 -*-
import textwrap
import time

import google.generativeai as genai
import markdown2
from IPython.display import Markdown
import yaml

from common.Res import ResData
from common.jsonEncoder import toJSON

with open("../../application.yml", "r") as f:
    config = yaml.safe_load(f)


key=config['ai']['key']['gemini']
genai.configure(api_key=key)

proxies={"http":"http://127.0.0.1:8090","https":"http://127.0.0.1:8090"}


import os
os.environ["HTTP_PROXY"] = "http://127.0.0.1:8090"
os.environ["HTTP_PROXYS"] = "http://127.0.0.1:8090"


model = genai.GenerativeModel('gemini-pro')
def to_markdown(text):
  text = text.replace('•', '  *')
  return Markdown(textwrap.indent(text, '> ', predicate=lambda _: True))


def common(content:str,start:float):
    response = model.generate_content(content)
    #data=to_markdown(response.text)
    try:
        data=format_markdown(response.text)
    except Exception as e:
        end=time.time()

        return ResData(end-start,err=e.__cause__,model="gemini-pro")
    else:
        end=time.time()
        return ResData(end-start,data=data,model="gemini-pro")

#流式响应


def my_stream(content:str,start:float):
    response = model.generate_content(content,stream=True)
    response.resolve()
    print(response.text+'1')
    print(1)
    try:
        data=format_markdown(response.text)
    except Exception as e:
        end=time.time()
        err=toJSON(ResData(end-start,err=e.__cause__,model="gemini-pro"))
        print(err)
        return err
    else:
        end=time.time()
        partData=toJSON(ResData(end-start,data=data,model="gemini-pro"))
        print(partData)
        yield partData

def format_markdown(text):
  indented_text = textwrap.indent(text, '> ', predicate=lambda _: True)
  return markdown2.markdown(indented_text)




if __name__ == '__main__':
    print(22)
    while True:
     my_stream("python 是什么",time.time())
