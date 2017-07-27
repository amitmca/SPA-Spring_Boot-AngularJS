package com.spa.blue.main.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.spa.blue.main.model.BlogPost;

public interface BlogPostRepo extends JpaRepository<BlogPost, Long > {
	
	BlogPost findByTitle(String title);
	
	BlogPost findById(Long id);

}
