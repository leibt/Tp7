package be.example.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.example.entities.Order;

/**
 * Servlet implementation class OrdersList
 */
@WebServlet("/orders-list")
public class OrdersList extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final String VIEW 		 	= "/WEB-INF/ordersList.jsp";
	private static final String ATT_LIST_ORDER 	= "ordersList";
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Map<Long, Order> orderMap = (Map<Long,Order>)session.getAttribute(ATT_LIST_ORDER);
		
		if(orderMap != null) {
			request.setAttribute(ATT_LIST_ORDER, orderMap);	
		}
				
		this.getServletContext().getRequestDispatcher(VIEW).forward(request,response);
	}
}