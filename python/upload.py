import falcon
import re

class Upload():
    def on_post(self, req, resp):
        path = req.path

        filename = ""

        m = re.search("^\/upload\/(.+)$", path)
        if m:
            filename = m.group(1)

        b = req.stream.read()
        f = open(filename, "w+")
        f.write(b)
        f.close()