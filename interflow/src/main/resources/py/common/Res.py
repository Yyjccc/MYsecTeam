import time


class ResData:

    def __init__(self,spareTime:float,model=None,data=None,err=None):
        self.model = model
        self.data=str(data)
        if err is not None:
            self.status="failed"
            self.err=err
        else:
            self.status="success"
        self.spare=spareTime
        self.time=int(time.time())

