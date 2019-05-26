<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>SHOW ORDERS</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
    	<%@ include file="/inc/menu.jsp" %>
    	<p class="info"><c:out value="${ form.msg }" /></p>
    	<c:set value="${order.client }" var="client" scope="request"/>
    
    	<p>Client</p>
	    <p>Name : <c:out value="${ client.name }" /></p>
  	    <p>Firstname : <c:out value="${ client.firstname }" /></p>
		<p>Address : <c:out value="${ client.address }" /></p>
		<p>Phone : <c:out value="${ client.phone }" /></p>
		<p>Email : <c:out value="${ client.email }" /></p>
		<p>Order</p>
		<p>Date  : <joda:format value="${order.date }" pattern="dd/MM/yy HH:mm:ss"></joda:format></p> 
		<p>Total  : <c:out value="${ order.total }" /></p> 
		<p>Payment method  : <c:out value="${ order.paymentMethod }" /></p> 
	    <p>Payment status  : <c:out value="${ order.paymentStatus }" /></p> 
		<p>Delivery method  : <c:out value="${ order.deliveryMethod }" /></p> 
		<p>Delivery status  : <c:out value="${ order.deliveryStatus }" /></p> 
    	        
    </body>
</html>