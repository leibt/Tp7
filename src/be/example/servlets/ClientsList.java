package be.example.servlets;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.example.entities.Client;

/**
 * Servlet implementation class ClientsList
 */
@WebServlet("/clients-list")
public class ClientsList extends HttpServlet {
	
	public static final String VIEW			= "/WEB-INF/clientsList.jsp";
	public static final String ATT_LIST_CLI	= "clientsList";
		
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Map<Long, Client> clientList = (Map<Long,Client>)session.getAttribute(ATT_LIST_CLI);
		
		if(clientList != null) {
			request.setAttribute(ATT_LIST_CLI, clientList);
		}
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}