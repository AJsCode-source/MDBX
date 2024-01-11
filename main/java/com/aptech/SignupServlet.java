package com.aptech;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/SignUp")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String url = "jdbc:mysql://localhost:3306/nigeriadb";
	String username = "root";
	String psw = "dr0w55@p";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Testing 123");
		try {
		    PrintWriter out = response.getWriter();
		    
		    // Database connectivity
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    Connection con = DriverManager.getConnection(url, username, psw);
		    
		    // Getting request parameters
		    String fullNameInput = request.getParameter("fullname");
		    String emailInput = request.getParameter("email");
		    String passwordInput = request.getParameter("password");
		    
		    // Check if values exist in the database using PreparedStatement
		    PreparedStatement ps = con.prepareStatement("SELECT fullname FROM register WHERE fullname=? OR email=?");
		    ps.setString(1, fullNameInput);
		    ps.setString(2, emailInput);
		    
		    // Execute query and get ResultSet
		    ResultSet rs = ps.executeQuery();
		    
		    // Check if the user already exists
		    if (rs.next()) {
		        out.print("<font color=red size=18>User already exists!<br>");
		        out.print("<a href=register.jsp>Try again</a>");
		    } else {
		        // If the user doesn't exist, insert into the database
		        PreparedStatement insertStatement = con.prepareStatement("INSERT INTO register (fullname, email, dob, password) VALUES (?, ?, ?, ?)");
		        insertStatement.setString(1, fullNameInput);
		        insertStatement.setString(2, emailInput);
		        insertStatement.setString(4, passwordInput);
		        
		        // Execute the insert statement
		        int rowsAffected = insertStatement.executeUpdate();
		        
		        if (rowsAffected > 0) {
		            // Registration successful
		            out.print("<font color=green size=18>Registration Successful!<br>");
		            out.print("<a href=login.jsp>Login</a>");
		        } else {
		            // Registration failed
		            out.print("<font color=red size=18>Registration Failed!<br>");
		            out.print("<a href=register.jsp>Try again</a>");
		        }

		        // Close resources
		        insertStatement.close();
		    }

		    // Close resources
		    rs.close();
		    ps.close();
		    con.close();
		} catch (ClassNotFoundException | SQLException ex) {
		    ex.printStackTrace();
		}
	}

}
