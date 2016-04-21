<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
<title>Login home page</title>
</head>


<body>
	<h1>Didriks user page</h1>
	<h2>Authentication</h2>
	
	<!-- variable isUserDeleted is sent from the delete process in the controller -->
	<c:if test="${isUserDeleted}">
		<h3>Sucessfully deleted user account, my friend! Welcome back
		</h3>
	</c:if>
	
	<!-- variable isNotValidUser is sent from the authentication in the controller -->
	<c:if test="${isNotValidUser}">
		<h3 style="color:red;font-size:14px">Wrong username OR/AND password
		</h3>
	</c:if>
	
	
	<form action="ControllerServlet" method="POST"> 
		
		<!-- for controller to identify what todo -->
		<input type="hidden" name="command" value="LOGIN">
		
		Enter username: <input type="text" name="usernameFromUser"> <br/><br/>
		Enter password: <input type="password" name="passwordFromUser"> <br/><br/>
		<input type="submit" value="Login">	
		
	</form>
	<br/><br/>
	<hr>
	<a href="register_page.jsp">Register yourself!</a>


</body>
</html>