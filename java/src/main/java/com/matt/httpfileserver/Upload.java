package com.matt.httpfileserver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

@SuppressWarnings("serial")
public class Upload extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getPathInfo();
				
		Pattern pattern = Pattern.compile("^\\/(.+)$");
		Matcher matcher = pattern.matcher(path);
		String filename = "";
		
		if (matcher.find()) {
			filename = matcher.group(1);
		}
		
		InputStream is = request.getInputStream();
		
		OutputStream os = new FileOutputStream(filename);
		
		IOUtils.copy(is, os);
		is.close();
		os.close();
	}

}
