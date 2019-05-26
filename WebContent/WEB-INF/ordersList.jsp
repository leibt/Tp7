<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Orders list</title>
<link type="text/css" rel="stylesheet" href="inc/style.css" /> 
</head>
<body>
	<c:import url="/inc/menu.jsp"/>
	
	
	<c:choose>
		<c:when test="${empty ordersList }">
			<p class="erreur">No orders recorded</p>
		</c:when>
		<c:otherwise>
		
			<table>
			  <tr>
			    <th>Client</th>
			    <th>Date</th>
			    <th>Total</th>
			    <th>Payment method</th>
			    <th>Payment status</th>
			    <th>Delivery method</th>
			    <th>Delivery status</th>
			    <th class="action">Action</th>
			  </tr>
			  
			  
			  <c:forEach var="order" items="${sessionScope.ordersList }">	
			  <c:set var="client" value="${order.value.client }" scope="request"></c:set>
			  	<tr>
			  		<td><c:out value="${ client.name } ${ client.firstname }" /></td>
			  		<td><c:out value="${ order.value.date }" /></td>
			  		<td><c:out value="${ order.value.total }" /></td>
			  		<td><c:out value="${ order.value.paymentMethod }" /></td>
			  		<td><c:out value="${ order.value.paymentStatus }" /></td>
			  		<td><c:out value="${ order.value.deliveryMethod }" /></td>
			  		<td><c:out value="${ order.value.deliveryStatus }" /></td>
			  		
			  		<td class="action"><a href="<c:url value="/delete-order"><c:param name="id" value="${order.value.id }" /></c:url>"><strong>X</strong></a></td>
			  	</tr>
			  </c:forEach>
			  	
			</table>
		</c:otherwise>
	</c:choose>

</body>
</html>