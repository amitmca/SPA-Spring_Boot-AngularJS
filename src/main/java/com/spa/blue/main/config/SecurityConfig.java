package com.spa.blue.main.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

	  private static final String LOCAL = "localhost:3306/spa_blue";

		  @Override
		  protected void configure(HttpSecurity http) throws Exception {
			  http
			  .httpBasic().and()
			  .authorizeRequests()
			  	.antMatchers("/index.html", "/pages/home.html", "/pages/login.html", "/", "/pages/create-user.html",
			  			"/add", "/login", "/user").permitAll()
			  	.anyRequest().authenticated()
			  	.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
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
		  
			
		  @Bean
		  public BasicAWSCredentials basicAWSCredentials() {
			  // change these to correct keys
			  return new BasicAWSCredentials("", "");
		  }
		 
		  @SuppressWarnings("deprecation")
		  @Bean
		  public AmazonS3Client amazonS3Client(BasicAWSCredentials awsCredentials) {
			  AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
			  amazonS3Client.setRegion(Region.getRegion(Regions.fromName("us-west-2")));
			  return amazonS3Client;
		  }
}
		  