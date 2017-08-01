package com.spa.blue.main.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import com.spa.blue.main.model.UserData;
import com.spa.blue.main.repository.UserRepo;

@RestController
public class UserController {
	
	private UserRepo userRepo;
	
	@Autowired
	public UserController(UserRepo uRepo) {
		this.userRepo = uRepo;
	}

	/**
	 * This creates a new user to access the mai site.
	 * @param username
	 * @param password
	 * @return saved message	
	 * @throws Exception 
	 */
	
	// use this request mapping for post containing text
	@RequestMapping(path="/add", method = RequestMethod.POST, 
				consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public Map<String, Object> addNewUser(@RequestBody UserData userData, HttpServletResponse response) {
				
		Map<String, Object> model = new HashMap<String, Object>();
			
        //Get connection
    	Connection con;
        Connection con2;
        Statement stmt = null;
                try{
                    String MySQL = "jdbc:mysql://localhost:3306/?user=&password=&useSSL=false";
                    String SQL = "insert into users (username, password, enabled) values ('" + userData.getUsername() + "', '" + userData.getPassword() + "', true)";
                    String SQL2 = "insert into user_roles (username, role) values ('" + userData.getUsername() + "', 'ROLE_USER')";
                    //Opens connection to the new selection
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection(MySQL);
                    con2 = DriverManager.getConnection(MySQL);
                    //Creates database
                    stmt = con.createStatement();
                    stmt.execute(SQL);
                    stmt = con2.createStatement();
                    stmt.execute(SQL2);
                    // save user inside here because 
                    // I only want to save if I am able 
                    // to establish this connection
                    // if the database save fails this will too!
                    UserData user = new UserData();
                    user.setUsername(userData.getUsername());
                    user.setPassword(userData.getPassword());
                    user.setFirstName(userData.getFirstName());
                    user.setLastName(userData.getLastName());
                    user.setEmail(userData.getEmail());
                    userRepo.save(user);
                } catch (SQLException e) {
                	System.out.println(e.getMessage());
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
        

                
        String saved = "The user " + userData.getUsername() + " has been saved.";
        model.put("saved", saved);
        return model;
	}
	
}
