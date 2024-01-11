package com.aptech;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	String url = "jdbc:mysql://localhost:3306/mdbx";
	String username = "root";
	String psw = "dr0w55@p";
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// PrintWriter
			PrintWriter out = response.getWriter();
			// Database connectivity
			// Loading the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Create connection
			Connection con = DriverManager.getConnection(url, username, psw);
			// Getting request parameter
			String emailInput = request.getParameter("email");
			String passwordInput = request.getParameter("password");
			
			// Check if value exist in database using preparedStaement
			PreparedStatement ps = con.prepareStatement("SELECT username FROM login WHERE email =? and password=?");
			ps.setString(1, emailInput);
			ps.setString(2, passwordInput);
			// execute query using EResultSet
			ResultSet rs = ps.executeQuery();
			
			// if user matched => redirect user to success page
			if(rs.next()) {
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			}else {
				out.print("<font color=red size=18>Login Failed!!!<br>");
				out.print("<a href=login.jsp>Try again</a>");
			}
		}catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}
	}

}
