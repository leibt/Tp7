<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Create client</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
        <div>
        	<c:import url="/inc/menu.jsp"></c:import>
            <form method="post" action="<c:url value='/create-client' />" enctype="multipart/form-data">
                
                <fieldset>
                	<legend>Client information</legend>
					<c:import url="/inc/formCli.jsp" />
				</fieldset>
                                
                <input type="submit" value="Confirm"  />
                <input type="reset" value="Undo" /> <br />
            </form>
        </div>
        <span class="${empty form.errors ? 'succes' : 'erreur' }">${form.msg }</span>
    </body>
</html>