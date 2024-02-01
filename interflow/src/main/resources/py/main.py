import time
from logging.config import dictConfig

from flask import *
import service.gemini as gemini
from common.jsonEncoder import toJSON

app=Flask(__name__)


@app.route('/gemini/common')
def gemini_common():
    start=time.time()
    arg=request.args.get('content')
    res=toJSON(gemini.common(arg,start=start))
    print('Response: %s', res)
    response=app.response_class(response=res,mimetype='application/json')
    return response


@app.route('/gemini/stream')
def gemini_stream():
    start = time.time()
    arg = request.args.get('content')
    return Response(stream_with_context(gemini.stream(arg,start)), content_type='text/plain')



if __name__=='__main__':
    app.run()


