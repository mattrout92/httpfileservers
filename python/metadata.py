import falcon
import re
import os

class Metadata():
    def on_get(self, req, resp):
        path = req.path

        filename = ""

        m = re.search("^\/metadata\/(.+)$", path)
        if m:
            filename = m.group(1)

        size = os.path.getsize(filename)

        resp.body = "{\"name\":\"" + filename + "\", \"size\":" + size + "}" 