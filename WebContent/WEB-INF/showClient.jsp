<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>SHOW CLIENT</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
    	<%@ include file="/inc/menu.jsp" %>
  		
		<p class="info">Info : <c:out value="${ form.msg }" /></p>	
		
  		<c:if test="${not error }">
		    <p>Name : <c:out value="${ client.name }" /></p>
		    <p>Firstname : <c:out value="${ client.firstname }" /></p>
		    <p>Address : <c:out value="${ client.address }" /></p>
		    <p>Phone : <c:out value="${ client.phone }" /></p>
		    <p>Email : <c:out value="${ client.email }" /></p>
		    <p>Image : <c:out value="${client.image }" /></p>
  		</c:if>	      
    </body>
</html>