<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>

<head>
<title>Admin home page</title>
</head>


<body>
	<h1>Didriks user page</h1>
	<h2>Admin</h2>
	
	<!-- Examples of java code in jsp-file, which is mostly against the conventions! -->
	
	<!-- JSP expression, the value of an expression is converted to a String  -->
	<%= new Date() %>
	
	<!-- JSP scriplet , A scriptlet can contain any number of JAVA language statements,
	 variable or method declarations, or expressions that are valid in the page scripting language. -->
	<% new Date(); System.out.println("Iam in a scriptlet"); %>

	<!-- JSP declaration,A declaration declares one or more variables 
	or methods that you can use in Java code later in the JSP file -->
	<%! int num; float num2; double num3; %>
	
	<!-- Examples of java code in jsp-file, which is mostly against the conventions! -->
	
	

	<form action="ControllerServlet">
		<!-- for controller to identify what todo -->
		<input type="hidden" name="command" value="SEARCH">
		
		<input type="text" name="searchString" value="">
		<input type="submit" value="Search me">
		<br/>
		Include in search:
		<input type="checkbox" name="searchType" value="user_name" checked> User name
		<input type="checkbox" name="searchType" value="first_name"> First name
		<input type="checkbox" name="searchType" value="last_name"> Last name
		<input type="checkbox" name="searchType" value="email"> Email
		<input type="checkbox" name="searchType" value="sex"> Sex
		<input type="checkbox" name="searchType" value="country"> Country
		<input type="checkbox" name="searchType" value="age"> Age
		<br/>
		<c:if test="${isNotChecked}">
			<p style="color:red">Check atleast one checkbox for the search!
			</p>
		</c:if>
		<br/>
	</form>
	
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
		
		<c:forEach var="tempUser" items="${USER_LIST}">
		
			<!-- set up a link to delete a student -->
			<!-- looks like: ControllerServlet?command=DELETE&userId= -->
			<!-- so we know which command and which student to delete in the servlet -->
			<c:url var="deleteLink" value="ControllerServlet">
				<c:param name="command" value="DELETE" />
				<c:param name="userId" value="${tempUser.id}" /> <!-- will automatically call get method. -->
				<c:param name="kindOfDelete" value="ADMIN_DELETE"/>
			</c:url>
			
			<c:url var="loadLink" value="ControllerServlet">
				<c:param name="command" value="LOAD" />
				<c:param name="userId" value="${tempUser.id}" />
				<c:param name="kindOfLoad" value="FROM_ADMIN"/>
			</c:url>
			
			<tr>
				<td>${tempUser.userName}</td> <!-- will automatically call get method. -->
				<td>${tempUser.password}</td>
				<td>${tempUser.firstName}</td>
				<td>${tempUser.lastName}</td>
				<td>${tempUser.email}</td>
				<td>${tempUser.sex}</td>
				<td>${tempUser.country}</td>
				<td>${tempUser.age}</td>
				<td>
					<a href="${deleteLink}">Delete</a>
				</td>
				<td>
					<a href="${loadLink}">Update</a>
				</td>
			</tr>
		</c:forEach>
	
	</table>
	
	<br/><br/>
	${STATISTIC_HTML_TABLE}
	<br/><br/>
	
	<hr>
	<input type="button" value="Logout" 
		onclick="window.location.href='login_page.jsp'"
	/>

</body>
</html>