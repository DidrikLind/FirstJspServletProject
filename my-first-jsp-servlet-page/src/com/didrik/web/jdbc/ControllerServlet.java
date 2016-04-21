package com.didrik.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO;
	private SqlUtil sqlUtil;
	
	// works with the context.xml file
	@Resource(name="jdbc/web_page_jsp_db")
	private DataSource dataSource; // connection pool, more specification aboout this in context.xml
	
	//called by the java ee server or by tomcat when the server is initilized
	//This acts like a constructor, but for an servlet
	@Override
	public void init() throws ServletException {
		super.init();
	
		try {
			userDAO = new UserDAO(dataSource);
			sqlUtil = new SqlUtil(dataSource);
		}catch(Exception exc) {
			System.out.println("HELLO");
			throw new ServletException(exc);
		}
	}


	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("get request");
		try {
			String theCommand = request.getParameter("command");
			
			if(theCommand == null) {
				theCommand = "LOGIN_PAGE";
			}
			
			switch(theCommand) {
			 	case "LOGIN_PAGE":
			 		RequestDispatcher dispatcher = request.getRequestDispatcher("/login_page.jsp");
			 		dispatcher.forward(request, response);
			 		break;
				case "LOGIN":
					authentication(request, response);
					break;
				case "REGISTER":
					addUser(request, response);
					break;
				case "DELETE":
					deleteUser(request, response);
					break;
				case "LOAD":
					//System.out.print("JEJ LOAD_USER");
					loadUser(request, response);
					break;
				case "UPDATE":
					updateUser(request, response);
					//System.out.println("HAR BETT OM DETTA! ;)");
					break;
				case "SEARCH":
					searchUsers(request, response);
					
			}

		} catch(Exception exc) {
			throw new ServletException(exc);
		}
	}

	
	
	private void searchUsers(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		// from text
		String searchStr = request.getParameter("searchString");
		
		// from checkboxes
		String[] searchTypes = request.getParameterValues("searchType");
		RequestDispatcher dispatcher;
		
		if(searchTypes!=null) {

			List<User> users = userDAO.searchUsers(searchTypes, searchStr);
			request.setAttribute("USER_LIST", users);
		}
		else {
			List<User> users = userDAO.getUsers();
			request.setAttribute("USER_LIST", users);
			request.setAttribute("isNotChecked", true);
		}
		
		// send to JSP page (view)  "RequestDispatcher = 'BegärdAvsändare'"
		dispatcher = request.getRequestDispatcher("/admin_home_page.jsp");
		dispatcher.forward(request, response);
	}



	private void updateUser(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		int id = Integer.parseInt(request.getParameter("userId"));
		int age = Integer.parseInt(request.getParameter("age"));
		//System.out.println("username: " + request.getParameter("userName"));
		userDAO.updateUser(new User(id,
				request.getParameter("userName"),
				request.getParameter("password"),
				request.getParameter("firstName"),
				request.getParameter("lastName"),
				request.getParameter("email"),
				request.getParameter("sex"),
				request.getParameter("country"),
				age
		));
				
		if(request.getParameter("kindOfUpdate").equals("FROM_ADMIN")) {
			listUsers(request, response);
		}
		else {
			User user = userDAO.getUser(request.getParameter("userId"));
			request.setAttribute("USER", user);
			RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/user_home_page.jsp");
			dispatcher.forward(request, response);
		}
	}

	
	
	private void loadUser(HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String id = request.getParameter("userId");
		//System.out.println("ID MOTHER FKR: " +id);
		
		request.setAttribute("USER", userDAO.getUser(id));
		
		// for register_update_page_X.jsp to identify which sex to fill in
		request.setAttribute("whichSexForRadio", userDAO.getUser(id).getSex());
		
		RequestDispatcher dispatcher;
		if(request.getParameter("kindOfLoad").equals("FROM_ADMIN")) {
			dispatcher = request.getRequestDispatcher("/register_update_page_admin.jsp");
		}
		else {
			dispatcher = request.getRequestDispatcher("/register_update_page_user.jsp");
		}
		dispatcher.forward(request, response);
	}

	
	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		String id = request.getParameter("userId");
		userDAO.deleteUser(id);
		
		if(request.getParameter("kindOfDelete").equals("ADMIN_DELETE")) {
			listUsers(request, response);
		}
		else {
			
			// sending variable to login_page.jsp, to read in the <c :if> tag.
			request.setAttribute("isUserDeleted", true);
			
			RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/login_page.jsp");
			dispatcher.forward(request, response);
		}
	}

	
	
	private void addUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int age = Integer.parseInt(request.getParameter("age"));
		User user = new User(request.getParameter("userName"),
				request.getParameter("password"),
				request.getParameter("firstName"),
				request.getParameter("lastName"),
				request.getParameter("email"),
				request.getParameter("sex"),
				request.getParameter("country"),
				age);
		userDAO.addUser(user);
		
		request.setAttribute("USERNAME", user.getUserName());
		RequestDispatcher dispatcher = 
			request.getRequestDispatcher("/register_welcome_page.jsp");
		dispatcher.forward(request, response);
	}



	private void authentication(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userName = request.getParameter("usernameFromUser");
		String password = request.getParameter("passwordFromUser");
		// ok
		if(userName.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
			listUsers(request, response);
		}
		else {
			boolean isValidUser = false;
			RequestDispatcher dispatcher;
			List<User> users = userDAO.getUsers();
			for(User user: users) {
				if(userName.equalsIgnoreCase(user.getUserName()) && password.equalsIgnoreCase(user.getPassword())) {
					//System.out.println(user.getId());
					request.setAttribute("USER", user);
					dispatcher = request.getRequestDispatcher("/user_home_page.jsp");
					dispatcher.forward(request, response);
					isValidUser=true;
					break;
				}
			}
			
			// you cannot log in
			if(!isValidUser) {
				request.setAttribute("isNotValidUser", true);
				dispatcher = request.getRequestDispatcher("/login_page.jsp");
				dispatcher.forward(request, response);
			}

		}
		
	}



//	private void listUser(HttpServletRequest request, HttpServletResponse response)
//		throws Exception {
//		
//		//String userId = request.getParameter("userId"); // from user_home_page.jsp
//		//System.out.println("user IDIDIDI in listuser method: " + userId);
//		
//		//User user = userDAO.getUser(userId);
//		//request.setAttribute("USER", user);
//		RequestDispatcher dispatcher = 
//			request.getRequestDispatcher("/user_home_page.jsp");
//		dispatcher.forward(request, response);
//	}

	
	
	private void listUsers(HttpServletRequest request, HttpServletResponse response)
		throws Exception{
		
		List<User> users = userDAO.getUsers();
		request.setAttribute("USER_LIST", users);
		request.setAttribute("STATISTIC_HTML_TABLE", sqlUtil.getStatisticHtmlTable());
		
		// send to JSP page (view)  "RequestDispatcher = 'BegärdAvsändare'"
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_home_page.jsp");
		dispatcher.forward(request, response);

	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}
