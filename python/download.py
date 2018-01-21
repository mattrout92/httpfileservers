import falcon
import re

class Download():
    def on_get(self, req, resp):
        path = req.path

        filename = ""

        m = re.search("^\/download\/(.+)$", path)
        if m:
            filename = m.group(1)

        f = open(filename, "r+")
        resp.body = f