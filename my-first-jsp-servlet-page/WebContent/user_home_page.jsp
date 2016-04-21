<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
<title>User home page</title>
</head>


<body>
	<h1>Didriks user page</h1>
	<h2>User</h2>
	
	
	<table border="2px">
	
		<tr>
			<th>User Name</th>
			<th>Password</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Email</th>
			<th>Sex</th>
			<th>Country</th>
			<th>Age</th>
			<th>***</th>
		<tr>
		
		<!-- set up a link to delete a student -->
		<!-- looks like: ControllerServlet?command=DELETE&userId= -->
		<!-- so we know which command and which student to delete in the servlet -->
		<c:url var="deleteLink" value="ControllerServlet">
			<c:param name="command" value="DELETE" />
			<c:param name="userId" value="${USER.id}" /> <!-- will automatically call get method. -->
			<c:param name="kindOfDelete" value="USER_DELETE"/>
			<c:param name="nameOfDeletedUser" value="${USER.firstName}"/>
		</c:url>
		
		<c:url var="loadLink" value="ControllerServlet">
			<c:param name="command" value="LOAD" />
			<c:param name="userId" value="${USER.id}" />
			<c:param name="kindOfLoad" value="FROM_USER"/>
		</c:url>
		
		<tr> <!-- USER we get from controller -->
			<td>${USER.userName}</td> <!-- will automatically call get method. -->
			<td>${USER.password}</td>
			<td>${USER.firstName}</td>
			<td>${USER.lastName}</td>
			<td>${USER.email}</td>
			<td>${USER.sex}</td>
			<td>${USER.country}</td>
			<td>${USER.age}</td>
			<td>
				<a href="${deleteLink}" onclick="if(!(confirm('Are you sure you want to delete your account?'))) return false">Delete</a>
				<a href="${loadLink}">Update</a>
			</td>
		</tr>

	</table>
	
	<br/><br/>
	<hr>
	<input type="button" value="Logout" 
		onclick="window.location.href='login_page.jsp'"
	/>
	
</body>
</html>