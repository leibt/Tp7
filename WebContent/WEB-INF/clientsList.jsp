<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Clients list</title>
	<link type="text/css" rel="stylesheet" href="inc/style.css" /> 
</head>
<body>

	<c:import url="/inc/menu.jsp"/>

	<c:choose>
		<c:when test="${empty clientsList }">
			<p class="erreur">No clients save</p>
		</c:when>
		<c:otherwise>
			<table>
			  <tr>
			    <th>Name</th>
			    <th>Firstname</th>
			    <th>Address</th>
			    <th>Phone</th>
			    <th>Email</th>
			    <th>Image</th>
			    <th class="action">Action</th>
			  </tr>
			  
			  <c:forEach var="client" items="${sessionScope.clientsList }" varStatus="flag">	
			  <c:set var="cl" value="${client.value }" scope="request"></c:set>
			  	<tr>
			  		<td><c:out value="${ cl.name }" /></td>
			  		<td><c:out value="${ cl.firstname }" /></td>
			  		<td><c:out value="${ cl.address }" /></td>
			  		<td><c:out value="${ cl.phone }" /></td>
			  		<td><c:out value="${ cl.email }" /></td>
			  		<td>
			  			<c:if test="${not empty cl.image }">
			  				<a target="_blank" href="<c:url value="/images/${cl.image }" />">OPEN</a>
			  			</c:if>
			  		</td>
			  		<td class="action"><a href="<c:url value='/delete-client' ><c:param name='idClient' value='${cl.id }' /></c:url>"><strong>X</strong></a></td>
			  	</tr>
			  </c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	

</body>
</html>