<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Create order</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
        <div>
        	<c:import url="/inc/menu.jsp"/>
            <form method="post" action="<c:url value="/create-order" />" enctype="multipart/form-data">
            	<c:set value="${order.client }" var="client" scope="request" />
            	           	
            	<fieldset>
					<legend>Order information</legend>
					
					<c:if test="${!empty clientsList}">
						<div class="q_new_cli">
							<label for="newClient">New client ? <span class="requis">*</span></label>  
							<input type="radio" class="choiceNewClient" name="choiceNewClient" value="newClient" checked /> Yes <input type="radio" class="choiceNewClient" name="choiceNewClient" value="oldClient" /> No
							<br/><br/>  
							  
							  <div class="oldClient">
							  	<select id="existingClientId" name="existingClientId">
							  		<option value="">Choose a client... </option>
							  		
							  		<c:forEach var="cli" items="${sessionScope.clientsList }">
							  			<option value="<c:out value='${cli.value.id }' />">${cli.value.name } ${cli.value.firstname} </option>
							  		</c:forEach>
								</select>
							  </div>
						</div>
					</c:if>
					<c:import url="/inc/formCli.jsp" />
				</fieldset>	
            	
               
                
                
                <fieldset>
                    <legend>Order information</legend>
                    
                    <label for="dateOrder">Date <span class="requis">*</span></label>
                    <input type="text" id="dateOrder" name="dateOrder" value="<c:out value='${order.date }' />" size="20" maxlength="20" disabled />
                    <br />
                    
                    <label for="totalOrder">Total <span class="requis">*</span></label>
                    <input type="text" id="totalOrder" name="totalOrder" value="<c:out value='${order.total }' />" size="20" maxlength="20" />
                    <span class="erreur" >${form.errors['totalOrder'] }</span>
                    <br />
                    
                    <label for="paymentMethodOrder">Payment method <span class="requis">*</span></label>
                    <input type="text" id="paymentMethodOrder" name="paymentMethodOrder" value="<c:out value='${order.paymentMethod }' />" size="20" maxlength="20" />
                    <span class="erreur" >${form.errors['paymentMethodOrder'] }</span>
                    <br />
                    
                    <label for="paymentStatusOrder">Payment status</label>
                    <input type="text" id="paymentStatusOrder" name="paymentStatusOrder" value="<c:out value='${order.paymentStatus }' />" size="20" maxlength="20" />
                    <span class="erreur" >${form.errors['paymentStatusOrder'] }</span>
                    <br />
                    
                    <label for="deliveryMethodOrder">Delivery Method <span class="requis">*</span></label>
                    <input type="text" id="deliveryMethodOrder" name="deliveryMethodOrder" value="<c:out value='${order.deliveryMethod }' />" size="20" maxlength="20" />
                    <span class="erreur" >${form.errors['deliveryMethodOrder'] }</span>
                    <br />
                    
                    <label for="deliveryStatusOrder">Delivery Status</label>
                    <input type="text" id="deliveryStatusOrder" name="deliveryStatusOrder" value="<c:out value='${order.deliveryStatus }' />" size="20" maxlength="20" />
                    <span class="erreur" >${form.errors['deliveryStatusOrder'] }</span>
                    <br />
                </fieldset>
                <input type="submit" value="Confirm"  />
                <input type="reset" value="Undo" /> <br />
            </form>
        </div>
        
        <span class="${empty form.errors ? 'succes' : 'erreur' }">${form.msg }</span>
        
        
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script> 
       	<script>
        	jQuery(document).ready(function(){
        		
        		$(".oldClient").hide();
        		console.log('test : '+$('.choiceNewClient').val());
        		
        		$('input[name=choiceNewClient]:radio').click(function(){
        			$(".oldClient").hide();
        			$(".newClient").hide();
        			$( "."+$(this).val() ).show();
        			console.log($(this).val());
        			        			
        		});
        	
            });
        </script>
        
    </body>
</html>

