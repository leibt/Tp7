package be.example.forms;

import static be.example.forms.FormUtilities.getFieldValue;
import static be.example.forms.FormUtilities.getFileName;
import static be.example.forms.FormUtilities.writeFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import be.example.dao.ClientDAO;
import be.example.entities.Client;
import be.example.exceptions.DAOException;
import be.example.exceptions.FormValidationException;
import eu.medsea.mimeutil.MimeUtil;

public class ClientForm {
	
	private static final String NAME_FIELD      = "nameClient";
	private static final String FIRSTNAME_FIELD = "firstnameClient";
	private static final String ADDRESS_FIELD   = "addressClient";
	private static final String PHONE_FIELD 	= "phoneClient";
	private static final String EMAIL_FIELD     = "emailClient";
	private static final String IMAGE_FIELD		= "imageClient";
	
	private static final int BUFFER_SIZE		= 10240; //10ko
	
	
	private Map<String,String> errors = new HashMap<>();
	private String msg;
	
	private ClientDAO clientDAO; 
	
	public ClientForm(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}
	
	public Client createClient(HttpServletRequest request, String path) {
		
		String name = getFieldValue(request,NAME_FIELD);
        String firstname = getFieldValue(request,FIRSTNAME_FIELD);
        String address = getFieldValue(request,ADDRESS_FIELD);
        String phone = getFieldValue(request,PHONE_FIELD);
        String email = getFieldValue(request,EMAIL_FIELD);
                
		Client cli = new Client();

    	checkName(name, cli);
		checkFirstname(firstname, cli);
		checkAddress(address,cli);
		checkPhone(phone,cli);
		checkEmail(email,cli);
		checkImage(request, path, cli);
        try {
        	if(errors.isEmpty()) {
        		clientDAO.create(cli);
    			msg="Successful of customer creation";
    		}else {
            	msg="Failure of customer creation ";
            }
        }catch(DAOException e) {
        	setError("msg", "ERROOOOOOOR !");
        	//e.printStackTrace();
        }
		return cli;
	}
	
	private void setError(String champ, String valeur) {
		errors.put(champ, valeur);
	}
	
	private void checkName(String name, Client client){
		try {
			if(name!=null) {
				if(name.trim().length() < 2)
					throw new FormValidationException("Name must contains at least 2 characters");	
			}else {
				throw new FormValidationException("Please enter a name");
			}
	    }catch(FormValidationException e) {
	       	setError(NAME_FIELD, e.getMessage());
	    }

        client.setName(name);
	}
	
	private void checkFirstname(String firstname, Client client){
		try {
			if(firstname !=null && firstname.trim().length() < 2) {
				throw new FormValidationException("Firstname must contains at least 2 characters");
			}
		}catch(FormValidationException e) {
			setError(FIRSTNAME_FIELD, e.getMessage());
		}
		
		client.setFirstname(firstname);
	}
	
	private void checkAddress(String address, Client cli) {
		try {
			if(address!=null) {
				if(address.trim().length() < 10) {
					throw new FormValidationException("The address must contains at least 10 characters");
				}		
			}else {
				throw new FormValidationException("Please enter an address");
			}
		}catch(FormValidationException e) {
			setError(ADDRESS_FIELD, e.getMessage());
		}
		cli.setAddress(address);
	}
	
	private void checkPhone(String phone, Client cli) {
		try {
			if(phone!=null) {
				if(!phone.matches("^\\d+$") ) {
					throw new FormValidationException("The phone number must contains only numbers");
				}else if(phone.trim().length() < 4) {
					throw new FormValidationException("The phone number must contains 4 numbers at least ");
				}
			}else {
				throw new FormValidationException("Please enter a phone number");
			}
		}catch(FormValidationException e) {
			setError(PHONE_FIELD, e.getMessage());
		}
		cli.setPhone(phone);
	}
	
	private void checkEmail(String email, Client cli) {
		try {
			if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" )) {
				throw new FormValidationException( "Please enter a valid email" );
		    } 
		}catch(FormValidationException e) {
			setError(EMAIL_FIELD, e.getMessage());
		}
		cli.setEmail(email);
	}
	
	private void checkImage(HttpServletRequest request, String path, Client cli){		
		String imageName = null;
		InputStream content = null;
		try {
			Part part = request.getPart(IMAGE_FIELD);
			
			imageName = getFileName(part);
			if(imageName != null && !imageName.isEmpty()) {
				imageName = imageName.substring(imageName.lastIndexOf("/") + 1).substring(imageName.lastIndexOf("\\")+1);			
				content = part.getInputStream();
					
				//MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
				//Collection<?> mimeTypes = MimeUtil.getMimeTypes(content);
				
				//if( mimeTypes.toString().startsWith("image") ) {
					try {
						writeFile( content, imageName, path, BUFFER_SIZE );
					}catch(Exception e) {
						e.printStackTrace();
						setError(IMAGE_FIELD,"Error writing "+e);
					}
				//}else {
				//	throw new FormValidationException("This file must be an image");
				//}
			}
			
		
		}catch(IllegalStateException e) {
			e.printStackTrace();
			setError(IMAGE_FIELD, "Image too big");
		}catch(IOException e) {
			e.printStackTrace();
			setError(IMAGE_FIELD, "Error server config");
		}catch(ServletException e) {
			e.printStackTrace();
			setError(IMAGE_FIELD, "This request type is not supported");
		}// catch (FormValidationException e) {
			//e.printStackTrace();
			//setError(IMAGE_FIELD, e.getMessage());
		//}
	
		cli.setImage(imageName);
	}
	
	
	private String validateDocument(HttpServletRequest request, String path) throws Exception{
		String nomFichier = null;
		InputStream contenu = null;
		
		try {
			Part part = request.getPart(IMAGE_FIELD);
			
			nomFichier = getFileName(part);
			if(nomFichier != null && !nomFichier.isEmpty()) {
				nomFichier = nomFichier.substring(nomFichier.lastIndexOf("/") + 1).substring(nomFichier.lastIndexOf("\\")+1);			
				contenu = part.getInputStream();
			}
			
			MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
			Collection<?> mimeTypes = MimeUtil.getMimeTypes(contenu);
			
			if( mimeTypes.toString().startsWith("image") ) {
				try {
					writeFile( contenu, nomFichier, path, BUFFER_SIZE );
					
				}catch(Exception e) {
					e.printStackTrace();
					setError(IMAGE_FIELD,"Error writing ");
				}
			}else {
				throw new Exception("This file must be an image");
			}
		
		}catch(IllegalStateException e) {
			e.printStackTrace();
			setError(IMAGE_FIELD, "Image too big");
		}catch(IOException e) {
			e.printStackTrace();
			setError(IMAGE_FIELD, "Error server config");
		}catch(ServletException e) {
			e.printStackTrace();
			setError(IMAGE_FIELD, "This request type is not supported");
		}
				
		return nomFichier;
	}
	 
	public Map<String, String> getErrors() {
		return errors;
	}

	public String getMsg() {
		return msg;
	}
	

}
