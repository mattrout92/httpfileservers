package com.matt.httpfileserver;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class Metadata extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getPathInfo();
		Pattern pattern = Pattern.compile("^\\/(.+)$");
		Matcher matcher = pattern.matcher(path);
		String filename = "";
		
		if (matcher.find()) {
			filename = matcher.group(1);
		}
		
		long size = new File(filename).length();
		
		JSONObject obj = new JSONObject();
		
		obj.put("size", size);
		obj.put("name", filename);
		
	    String out = obj.toString();
	    
	    OutputStream os = response.getOutputStream();
	    os.write(out.getBytes());
		
	}
	
}
