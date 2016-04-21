<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*, com.didrik.web.jdbc.*"%>
<!DOCTYPE html>
<html>

<head>
<title>Register page</title>
</head>


<body>
	<h1>Didriks user page</h1>
	<h2>Register</h2>
	
	<form action="ControllerServlet" method="GET">
		
		<!-- for controller to identify what TODO -->
		<input type="hidden" name="command" value="REGISTER">
		
		<table>
			<tbody>
				<tr>
			 		<td><label>User Name: </label></td>
			 		<td><input type="text" name="userName"></td>		 						 		
				</tr>
				
				<tr>
			 		<td><label>Password: </label></td>
			 		<td><input type="text" name="password"></td>					
				</tr>
				
				<tr>
			 		<td><label>First Name:</label></td>
			 		<td><input type="text" name="firstName"></td>						
				</tr>
				
				<tr>
			 		<td><label>Last Name:</label></td>
			 		<td><input type="text" name="lastName"></td>						
				</tr>
				
				<tr>
			 		<td><label>Email:</label></td>
			 		<td><input type="text" name="email"></td>						
				</tr>
				
				<tr>
			 		<td><label>Sex:</label></td>
			 		<!--  <td><input type="text" name="sex"></td>	-->
			 		<td>
			 			<input type="radio" name="sex" value="m">Male
			 			<input type="radio" name="sex" value="f">Female 
			 			<input type="radio" name="sex" value="mix">Mix
			 		</td>					
				</tr>
				
				<tr>
			 		<td><label>Country:</label></td>
			 		<td><input type="text" name="country"></td>						
				</tr>
				
				<tr>
			 		<td><label>Age:</label></td>
			 		<td><input type="text" name="age"></td>						
				</tr>
				
				<tr>
			 		<td><label> </label></td>
			 		<td><input type="submit" value="Register !"></td>					
				</tr>				
			</tbody>
		</table>
	</form>
	
	<br/><br/>
	<hr>
	<a href="login_page.jsp">Back to login page!</a>
	

</body>
</html>