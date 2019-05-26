package be.example.servlets;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.example.dao.ClientDAO;
import be.example.entities.Client;
import be.example.exceptions.DAOException;

/**
 * Servlet implementation class DeleteClient
 */
@WebServlet("/delete-client")
public class DeleteClient extends HttpServlet {
	
	public static final String ATT_LIST_CLI		= "clientsList";
	public static final String VIEW_LIST_CLI	= "/clients-list";
	public static final String ATT_DAO_FACTORY	= "daoFactory";
   
	@EJB
	private ClientDAO clientDAO;
    
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String idClient = (String) request.getParameter("idClient");
		Long id = Long.parseLong(idClient); 	
		
		HttpSession session = request.getSession();
		Map<Long,Client> cliList = (Map<Long, Client>) session.getAttribute(ATT_LIST_CLI);
		
		if(cliList != null) {
			try {
				clientDAO.delete(cliList.get(id));
				
				cliList.remove(id);
			}catch(DAOException e) {
				System.out.println("ERROR LEI : " +e);
				e.printStackTrace();
			}
			
			session.setAttribute(ATT_LIST_CLI, cliList);
		}
		response.sendRedirect(request.getContextPath()+ VIEW_LIST_CLI);
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
