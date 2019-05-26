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
import be.example.dao.OrderDAO;
import be.example.entities.Client;
import be.example.entities.Order;
import be.example.forms.OrderForm;

/**
 * Servlet implementation class CreateOrder
 */
@WebServlet(urlPatterns="/create-order", initParams = @WebInitParam(name="path", value="C:/Users/Leïla/Pictures/Test/"))
@MultipartConfig( location = "C:/Users/Leïla/Pictures/Test", maxFileSize = 10485760, maxRequestSize = 52428800, fileSizeThreshold = 1048576 )
public class CreateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public static final String ATT_ORDER     	= "order";
    public static final String ATT_FORM		    = "form";
    public static final String ATT_LIST_CLI		= "clientsList";
    public static final String ATT_LIST_ORDER	= "ordersList";
    public static final String VIEW_SUCCES      = "/WEB-INF/showOrder.jsp";
    public static final String VIEW_FORM        = "/WEB-INF/createOrder.jsp";
    
    @EJB
    private ClientDAO clientDAO;
    @EJB
    private OrderDAO orderDAO;
    
    
 	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Map<String, Client> clientsMap = (Map<String,Client>)session.getAttribute(ATT_LIST_CLI);
		
		if(clientsMap != null) {
			request.setAttribute(ATT_LIST_CLI, clientsMap);
		}
		
		
		this.getServletContext().getRequestDispatcher(VIEW_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = this.getServletConfig().getInitParameter("path");		
		OrderForm form = new OrderForm(clientDAO, orderDAO);
		Order order = form.createOrder(request,path);
		HttpSession session = request.getSession();
		
		String choiceNewClient = (String) request.getAttribute("choiceNewClient");

		
		request.setAttribute( ATT_ORDER, order );
		request.setAttribute( ATT_FORM, form );
		
		
        if(form.getErrors().isEmpty()) {
        	
        	Map<Long, Client> cliList = (HashMap<Long, Client>) session.getAttribute(ATT_LIST_CLI);
        	if(cliList == null)
        		cliList = new HashMap<Long, Client>();
        	
        	Client cl = order.getClient();
        	if(! cliList.containsValue( cl.getId() ) ) {
        		cliList.put(cl.getId(), cl);
        		session.setAttribute(ATT_LIST_CLI, cliList);
        	}
        	
        	Map<Long, Order> commandeList = (HashMap<Long,Order>) session.getAttribute(ATT_LIST_ORDER);
        	if(commandeList == null) {
        		commandeList = new HashMap<>();
        	}
        	
        	commandeList.put(order.getId(),order);
        	session.setAttribute(ATT_LIST_ORDER, commandeList);
        	this.getServletContext().getRequestDispatcher(VIEW_SUCCES).forward(request, response);
        }else {
        	this.getServletContext().getRequestDispatcher(VIEW_FORM).forward(request, response);
        }
	}

}
