package com.spa.blue.main.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spa.blue.main.model.BlogPart;
import com.spa.blue.main.model.BlogPost;
import com.spa.blue.main.repository.BlogPostRepo;

@RestController
public class BlogPostController {
	
	private BlogPostRepo blogRepo;
	
	@Autowired
	public BlogPostController(BlogPostRepo bRepo) {
		this.blogRepo = bRepo;
	}
	
	// use this request mapping for post containing text
	@RequestMapping(path="/post-blog", method = RequestMethod.POST, 
			consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public Map<String, Object> postBlog(@RequestBody BlogPart blogPart, HttpServletRequest req)
	{
		Map<String, Object> model = new HashMap<String, Object>();
		
		String author = req.getRemoteUser();
		
		BlogPost post = new BlogPost();
		post.setTopic(blogPart.getTopic());
		post.setTitle(blogPart.getTitle());
		post.setContent(blogPart.getContent());
		post.setAuthor(author);
		post.setDate(new Date().toString());
		blogRepo.save(post);
		post = blogRepo.findByTitle(blogPart.getTitle());
		Long id = post.getId();
		
		String saved = "The blog post with title: " + blogPart.getTitle() + ", and id: " + id + " has been saved.";
		model.put("saved", saved);
		return model;
	}
		
	@RequestMapping("/resource")
	public Map<String, ArrayList<BlogPost>> home() {
		
		Map<String, ArrayList<BlogPost>> model = new HashMap<String, ArrayList<BlogPost>>();
		
		ArrayList<BlogPost> fullList = (ArrayList<BlogPost>) blogRepo.findAll(); // repo returns list
		ArrayList<BlogPost> blogList = new ArrayList<BlogPost>();
		if (fullList.size() >= 5) {
			for (int i = fullList.size()-1; i > fullList.size()-6; i--) {
				blogList.add(fullList.get(i));
			}
			model.put("blogList", blogList);
		} else if (fullList.size() < 5) {
			model.put("blogList", fullList);
		}
		// just returns empty otherwise which doesn't seem to cause angular errors
		return model;
	}
	
	@RequestMapping(path="/view-blog/{id}")
	public Map<String, BlogPost> viewPost(@PathVariable String id) {
		Map<String, BlogPost> model = new HashMap<String, BlogPost>();
		Long realId = Long.parseLong(id);
		BlogPost post = blogRepo.findById(realId);
		model.put("blog", post);
		return model;
	}
	
	@RequestMapping(path="/list-all-blog-post")
	public Map<String, ArrayList<BlogPost>> allBlogPost() {
		Map<String, ArrayList<BlogPost>> model = new HashMap<String, ArrayList<BlogPost>>();
		model.put("blogList", (ArrayList<BlogPost>) blogRepo.findAll());
		return model;
	}
}




















