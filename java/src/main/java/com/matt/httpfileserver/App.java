package com.matt.httpfileserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * The simplest possible Jetty server.
 */
public class App
{
    public static void main( String[] args ) throws Exception
    {
        Server server = new Server(3030);
        
        ServletHandler handler = new ServletHandler();
        
        handler.addServletWithMapping(Download.class, "/download/*");
        handler.addServletWithMapping(Upload.class, "/upload/*");
        handler.addServletWithMapping(Metadata.class, "/metadata/*");
        
        server.setHandler(handler);
        server.start();
        server.join();
    }
}