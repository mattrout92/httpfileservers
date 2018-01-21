import falcon
import upload
import download
import metadata

class MyService(falcon.API):
    def __init__(self, cfg):
        self.cfg = cfg

        self.add_route('/upload/*', upload)
        self.add_route('/download/*', download)
        self.add_route('/metadata/*', metadata)

    def start(self):
        """ A hook to when a Gunicorn worker calls run()."""
        pass

    def stop(self, signal):
        """ A hook to when a Gunicorn worker starts shutting down. """
        pass

# falcon.API instances are callable WSGI apps
app = falcon.API()

# Resources are represented by long-lived class instances
things = MyService(app)

