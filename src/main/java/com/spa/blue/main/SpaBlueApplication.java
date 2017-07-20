package com.spa.blue.main;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@SpringBootApplication
public class SpaBlueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaBlueApplication.class, args);
	}
	
	  @Configuration
	  @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	  protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		 
		  private static final String LOCAL = "localhost:3306/";
		  
		  @Override
		  protected void configure(HttpSecurity http) throws Exception {
			  http
		        .httpBasic()
		      .and()
			  .authorizeRequests()
			  	.antMatchers("/index.html", "/home.html", "/login.html", "/", "/create-user.html",
			  			"/add", "/login", "/user").permitAll()
			  	.anyRequest().authenticated()
			  	.and().csrf().disable();
		  }
	    
		  // these are the credentials to login to database
		  @Bean(name = "dataSource")
		  public DriverManagerDataSource dataSource() {
			DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
			driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
			driverManagerDataSource.setUrl("jdbc:mysql://" + LOCAL);
			driverManagerDataSource.setUsername("");
			driverManagerDataSource.setPassword("");
			return driverManagerDataSource;
		  }

		  // autowire the credentials
		  @Autowired
		  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			  DataSource ds = dataSource();
		      // select username and password from database
		      auth.jdbcAuthentication().dataSource(ds)
			.usersByUsernameQuery(
				"select username,password, enabled from users where username=?")
			.authoritiesByUsernameQuery(
				"select username, role from user_roles where username=?");
		  }		
	  }
	  
}
