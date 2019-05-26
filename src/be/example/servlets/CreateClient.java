package be.example.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.example.dao.ClientDAO;
import be.example.entities.Client;
import be.example.forms.ClientForm;

/**
 * Servlet implementation class CreateClient
 */
@WebServlet(urlPatterns="/create-client", initParams = @WebInitParam(name="path", value="C:/Users/Leïla/Pictures/Test/"))
@MultipartConfig( location = "C:/Users/Leïla/Pictures/Test", maxFileSize = 10485760, maxRequestSize = 52428800, fileSizeThreshold = 1048576 )
public class CreateClient extends HttpServlet {
 
	private static final String ATT_CLIENT      = "client";
	private static final String ATT_FORM	    = "form";
	private static final String ATT_LIST_CLI	= "clientsList";
	private static final String ATT_PATH		= "path";
 
    public static final String VIEW_SUCCES    	= "/WEB-INF/showClient.jsp";
    public static final String VIEW_FORM    	= "/WEB-INF/createClient.jsp";
	
    @EJB
    private ClientDAO clientDAO;
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher(VIEW_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = this.getServletConfig().getInitParameter(ATT_PATH);
		ClientForm fc = new ClientForm(clientDAO);
		Client client = fc.createClient(request,path);
		
		HttpSession session = request.getSession();
				
        request.setAttribute( ATT_CLIENT, client );
        request.setAttribute( ATT_FORM, fc );

        
        if(fc.getErrors().isEmpty()) {
        	Map<Long, Client> clientList = (HashMap<Long, Client>) session.getAttribute(ATT_LIST_CLI); 
        	
        	if(clientList == null)
        		clientList = new HashMap<>();
        	
        	clientList.put(client.getId(),client);
        	session.setAttribute("clientList", clientList);
        	this.getServletContext().getRequestDispatcher(VIEW_SUCCES).forward(request, response);
        }else {
        	this.getServletContext().getRequestDispatcher(VIEW_FORM).forward(request, response);
        }
        	
	}

}
