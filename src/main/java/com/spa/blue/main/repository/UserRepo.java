package com.spa.blue.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spa.blue.main.model.UserData;

public interface UserRepo extends JpaRepository <UserData, Long> {
	
	UserData findById(Long id);
	
	UserData findByUsername(String username);

}
