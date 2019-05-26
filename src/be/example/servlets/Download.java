package be.example.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Download
 */
@WebServlet(urlPatterns="/images/*", initParams= @WebInitParam(name="path", value="Users/Leïla/Pictures/Test/"))
public class Download extends HttpServlet {
	
	private static final String ATT_PATH = "path";
	private static final int BUFFER_SIZE = 10240;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = this.getServletConfig().getInitParameter(ATT_PATH);
		String fileReq = request.getPathInfo();
				
		if(fileReq == null || "/".equals(fileReq)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		fileReq = URLDecoder.decode(fileReq,"UTF-8");
		File file = new File(path,fileReq);
		
		
		if( !file.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}		
		
		String type = getServletContext().getMimeType(file.getName());
		
		if ( type == null ) {
            type = "application/octet-stream";
        }
		
		response.reset();
		response.setBufferSize(BUFFER_SIZE);
		response.setContentType(type);
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "inline; filename=\""+file.getName()+"\"");
		
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		
		try {
			in = new BufferedInputStream(new FileInputStream(file),BUFFER_SIZE);
			out = new BufferedOutputStream(response.getOutputStream(), BUFFER_SIZE);
			
			byte[] buffer = new byte[BUFFER_SIZE];
			int size;
			while( ( size = in.read(buffer) ) > 0 ) {
				out.write(buffer, 0, size);
			}
		}finally {
			try {
				out.close();
			}catch(IOException e) {}
			try {
				in.close();
			}catch(IOException e) {}
		}
		
		this.getServletContext().getRequestDispatcher(path).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

}
