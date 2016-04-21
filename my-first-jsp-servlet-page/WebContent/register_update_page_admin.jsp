<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>

<head>
<title>Register page</title>
</head>


<body>
	<h1>Didriks user page</h1>
	<h2>Update Register</h2>
	
	<form action="ControllerServlet" method="GET">
		
		<!-- for controller to identify what TODO -->
		<input type="hidden" name="command" value="UPDATE">
		<input type="hidden" name="userId" value="${USER.id}">
		<input type="hidden" name="kindOfUpdate" value="FROM_ADMIN">
		
		<table>
			<tbody>
				<tr>
			 		<td><label>User Name: </label></td>
			 		<td><input type="text" name="userName" value="${USER.userName}"></td>		 						 		
				</tr>
				
				<tr>
			 		<td><label>Password: </label></td>
			 		<td><input type="text" name="password" value="${USER.password}"></td>					
				</tr>
				
				<tr>
			 		<td><label>First Name:</label></td>
			 		<td><input type="text" name="firstName" value="${USER.firstName}"></td>						
				</tr>
				
				<tr>
			 		<td><label>Last Name:</label></td>
			 		<td><input type="text" name="lastName" value="${USER.lastName}"></td>						
				</tr>
				
				<tr>
			 		<td><label>Email:</label></td>
			 		<td><input type="text" name="email" value="${USER.email}"></td>						
				</tr>
				
				<tr>
			 		<td><label>Sex:</label></td>
			 		<!--  <td><input type="text" name="sex"></td>	-->
			 		<td>
			 			<c:choose>
				
							<c:when test="${fn:endsWith(whichSexForRadio, 'm')}">
								<input type="radio" name="sex" value="m" checked>Male
			 					<input type="radio" name="sex" value="f">Female 
			 					<input type="radio" name="sex" value="mix">Mix
							</c:when>
							
							<c:when test="${fn:endsWith(whichSexForRadio, 'f')}">
								<input type="radio" name="sex" value="m">Male
			 					<input type="radio" name="sex" value="f" checked>Female 
			 					<input type="radio" name="sex" value="mix">Mix
							</c:when>
					
							<c:otherwise>
								<input type="radio" name="sex" value="m">Male
			 					<input type="radio" name="sex" value="f">Female 
			 					<input type="radio" name="sex" value="mix" checked>Mix
							</c:otherwise>

						</c:choose>

			 		</td>						
				</tr>
				
				<tr>
			 		<td><label>Country:</label></td>
			 		<td><input type="text" name="country" value="${USER.country}"></td>						
				</tr>
				
				<tr>
			 		<td><label>Age:</label></td>
			 		<td><input type="text" name="age" value="${USER.age}"></td>						
				</tr>
				
				<tr>
			 		<td><label> </label></td>
			 		<td><input type="submit" value="Update the user account!"></td>				
				</tr>				
			</tbody>
		</table>
	</form>
	
	<br/><br/>
	<hr>

	
</body>
</html>