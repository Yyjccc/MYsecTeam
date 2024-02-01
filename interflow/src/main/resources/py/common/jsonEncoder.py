import json

from common.Res import ResData


# 自定义 JSON 编码器
class DataEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj,ResData ):
            if(obj.status=="failed"):
                return {"ok":False ,"err": obj.err,"model":obj.model,"spare":obj.spare,"time":obj.time}
            else:
                return {"ok":True,"data":obj.data,"model":obj.model,"spare":obj.spare,'time':obj.time}

        return super().default(obj)

def toJSON(obj:ResData):
    return json.dumps(obj,cls=DataEncoder,ensure_ascii=False)