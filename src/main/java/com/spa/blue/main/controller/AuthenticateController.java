package com.spa.blue.main.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController {

	@RequestMapping("/user")
	public Principal user(Principal user) {
	  return user;
	}
	 
}
