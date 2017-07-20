package com.spa.blue.main.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

	/**
	 * This creates a new user to access the mai site.
	 * @param username
	 * @param password
	 * @return saved message	
	 * @throws Exception 
	 */
	
	@RequestMapping(path="/add")
	public String addNewUser (@RequestParam String username
			, @RequestParam String password) throws Exception {
		
        //Get connection
    	Connection con;
        Connection con2;
        Statement stmt = null;
                try{
                    String MySQL = "jdbc:mysql://localhost:3306/?user=&password=&useSSL=false";
                    String SQL = "insert into users (username, password, enabled) values ('" + username + "', '" + password + "', true)";
                    String SQL2 = "insert into user_roles (username, role) values ('" + username + "', 'ROLE_USER')";
                    //Opens connection to the new selection
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection(MySQL);
                    con2 = DriverManager.getConnection(MySQL);
                    //Creates database
                    stmt = con.createStatement();
                    stmt.execute(SQL);
                    stmt = con2.createStatement();
                    stmt.execute(SQL2);
                } catch (SQLException e) {
                	System.out.println(e.getMessage());
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
        return "redirect:/";
	}
	
}
