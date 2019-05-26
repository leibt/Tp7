package be.example.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import be.example.dao.ClientDAO;
import be.example.dao.OrderDAO;
import be.example.entities.Client;
import be.example.entities.Order;

/**
 * Servlet Filter implementation class SessionFilter
 */
@WebFilter("/*")
public class SessionFilter implements Filter {

	private static final String ATT_LIST_CLI	= "clientsList";
    public static final String ATT_LIST_ORDER	= "ordersList";

    @EJB
	private ClientDAO clientDAO;
    @EJB
	private OrderDAO orderDAO;
	
    /**
     * Default constructor. 
     */
    public SessionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		if(session.getAttribute(ATT_LIST_CLI) == null) {
			
			List<Client> clientsList = clientDAO.readAll();
			
			if(clientsList != null) {
				Map<Long,Client> clientsMap = new HashMap<>();
				
				for(Client cli : clientsList ) {
					clientsMap.put(cli.getId(), cli);
				}
				session.setAttribute(ATT_LIST_CLI, clientsMap);
			}
		}
		
		if(session.getAttribute(ATT_LIST_ORDER) == null) {
			List<Order> ordersList = orderDAO.readAll();
			
			if(ordersList != null) {
				Map<Long,Order> ordersMap = new HashMap<>();
				for(Order order : ordersList) {
					ordersMap.put(order.getId(), order);
				}
				session.setAttribute(ATT_LIST_ORDER, ordersMap);
			}
		}
		
		
		chain.doFilter(req, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
