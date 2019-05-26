package be.example.forms;

import static be.example.forms.FormUtilities.getFieldValue;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import be.example.dao.ClientDAO;
import be.example.dao.OrderDAO;
import be.example.entities.Client;
import be.example.entities.Order;
import be.example.exceptions.FormValidationException;

public class OrderForm {

    public static final String DATE_FIELD             	= "dateOrder";
    public static final String TOTAL_FIELD          	= "totalOrder";
    public static final String PAYMENT_METHOD_FIELD  	= "paymentMethodOrder";
    public static final String PAYMENT_STATUS_FIELD 	= "paymentStatusOrder";
    public static final String DELIVERY_METHOD_FIELD	= "deliveryMethodOrder";
    public static final String DELIVERY_STATUS_FIELD 	= "deliveryStatusOrder";
    public static final String CLIENT_CHOICE_FIELD	  	= "choiceNewClient";
    public static final String CLIENT_ID_EXI_FIELD		= "existingClientId";
    
    public static final String FORMAT_DATE            	= "dd/MM/yyyy HH:mm:ss";
    public static final String ATT_LIST_CLI			  	= "clientsList";
    
    private Map<String,String> errors;
	private String msg;
    
	private ClientDAO clientDAO;
	private OrderDAO orderDAO;
	
	public OrderForm(ClientDAO clientDAO, OrderDAO orderDAO) {
		this.clientDAO = clientDAO;
		this.orderDAO = orderDAO;
	}
    
    public Order createOrder (HttpServletRequest request, String path) {
    	
    	String paymentMethod 	= getFieldValue(request, PAYMENT_METHOD_FIELD );
        String paymentStatus 	= getFieldValue(request, PAYMENT_STATUS_FIELD );
        String deliveryMethod 	= getFieldValue(request, DELIVERY_METHOD_FIELD );
        String deliveryStatus 	= getFieldValue(request, DELIVERY_STATUS_FIELD );   
        String total		 	= getFieldValue(request, TOTAL_FIELD );
    	
        String isnewClient 		= getFieldValue(request, CLIENT_CHOICE_FIELD);
        String label_newClient 	= "newClient";
       
        Client cli;
        Order order = new Order();
        
        if( "oldClient".equals(isnewClient) ) {
        	
			String idClient_temp = getFieldValue(request, CLIENT_ID_EXI_FIELD);
        	Long idClient = Long.parseLong(idClient_temp);
        	
    		HttpSession session = request.getSession();
			Map<Long,Client> cliList = (HashMap<Long,Client>)session.getAttribute(ATT_LIST_CLI);
						
			cli = (Client) cliList.get(idClient);

			errors = new HashMap<>();
    	}else {
    		ClientForm fc = new ClientForm(clientDAO);
    	    cli = fc.createClient(request,path);
    	    errors=fc.getErrors();
    	}
        order.setClient(cli);
    	
        DateTime date = new DateTime();
        order.setDate(date);
        
        checkTotal(total,order);
        checkMethod(PAYMENT_METHOD_FIELD, paymentMethod, order);
        checkStatus(PAYMENT_STATUS_FIELD, paymentStatus, order);
        checkMethod(DELIVERY_METHOD_FIELD, deliveryMethod, order);
        checkStatus(DELIVERY_STATUS_FIELD, deliveryStatus, order);      
        
        if(errors.isEmpty()) {
        	orderDAO.create(order);
        	msg="Successful of order creation";
        }else {
        	msg="Failure of order creation";
        }
    	
    	return order;
    }
    
    private void checkTotal(String total_temp,Order order) {
        double total = 0;
        
        try {
        	if(total_temp != null) {		
        		try {
        			total = Double.parseDouble(total_temp);
            		if(total < 0) 
            			throw new FormValidationException("Please enter a valid total");
           
                } catch ( NumberFormatException e ) {
                	total= -1;
                	throw new FormValidationException("Please enter a valid total");
                }
   
        	}else {
        		throw new FormValidationException("Please enter a total");
        	}        	
        }catch(FormValidationException e) {
        	setError(TOTAL_FIELD, e.getMessage());
        }
        order.setTotal(total);
    }
        
    private void checkMethod(String label, String methodValue, Order order) {
    	try {
    		if(methodValue != null) {
        		if(methodValue.trim().length() < 2)
        			throw new FormValidationException("This field must contains at least 2 characters");
        	}else {
        		throw new FormValidationException("Please enter a valid method");
        	}
        }catch(FormValidationException e) {
        	switch(label) {
        		case PAYMENT_METHOD_FIELD:
        			setError(PAYMENT_METHOD_FIELD,e.getMessage());
        		break;
        		case DELIVERY_METHOD_FIELD:
        			setError(DELIVERY_METHOD_FIELD,e.getMessage());
        		break;	
        	}	
        }
    	
    	switch(label) {
			case PAYMENT_METHOD_FIELD:
				order.setPaymentMethod(methodValue);
			break;
			case DELIVERY_METHOD_FIELD:
				order.setDeliveryMethod(methodValue);
			break;	
		}		
    }
    
    private void checkStatus(String label, String statusValue, Order order) {
    	 try {
    		if(statusValue != null) {
    	    	if(statusValue.trim().length() < 2) {
    	       		throw new FormValidationException("This field must contains at least 2 characters");
    	    	}
    	    }
         }catch(FormValidationException e) {
        	 switch(label) {
	 			case PAYMENT_METHOD_FIELD:
	 				setError(PAYMENT_STATUS_FIELD,e.getMessage());
	 			break;
	 			case DELIVERY_METHOD_FIELD:
	 				setError(DELIVERY_STATUS_FIELD,e.getMessage());
	 			break;	
	 		}          	
         }
    	 
    	 switch(label) {
			case PAYMENT_METHOD_FIELD:
				order.setPaymentStatus(statusValue);
			break;
			case DELIVERY_METHOD_FIELD:
				order.setDeliveryStatus(statusValue);
			break;	
		}
    }
    
    
    private void setError(String champ, String valeur) {
		errors.put(champ, valeur);
	}

	public String getMsg() {
		return msg;
	}

	public Map<String, String> getErrors() {
		return errors;
	}
		
}
