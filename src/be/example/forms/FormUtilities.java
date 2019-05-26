package be.example.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class FormUtilities {
	
	protected static String getFieldValue(HttpServletRequest request, String nomChamp) {
		String value = request.getParameter(nomChamp);
		if(value == null || value.trim().length() == 0 ) {
			return null;
		}else {
			return value.trim();
		}
	}
	
	protected static String getFileName(Part part) {
		for( String contentDisposition : part.getHeader("content-disposition").split(";") ) {
			if( contentDisposition.trim().startsWith("filename") ) {
				return contentDisposition.substring(contentDisposition.indexOf("=") + 1).trim().replace("\"", "");
			}
		}
		return null;	
	}
	
	protected static void writeFile(InputStream content, String documentName, String path, int buffer_size) throws Exception{
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		
		try {
			
			in = new BufferedInputStream(content, buffer_size);
			out = new BufferedOutputStream( new FileOutputStream( new File( path + documentName ) ), buffer_size );
			
			byte[] buffer = new byte[buffer_size];
			int size = 0;
			while( ( size = in.read(buffer) ) > 0) {
				//System.out.println("ECRITURE : "+buffer);
				out.write(buffer,0,size);
			}
		}catch(SecurityException e) {
			System.err.println("ERRRROOOR : "+e);
		}catch(Exception e) {
			System.err.println("ERRRROOOR 2 : "+e);
		}finally {
			try {
				in.close();
			}catch(IOException e) {
				System.err.println("Error : "+e);
			}
			try {
				out.close();
			}catch(IOException e) {
				System.err.println("Error : "+e);
			}
		}
	}

}
