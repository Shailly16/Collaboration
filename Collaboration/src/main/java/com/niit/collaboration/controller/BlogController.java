package com.niit.collaboration.controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.BlogDAO;
import com.niit.collaboration.model.Blog;
import com.niit.collaboration.model.Blog;

@RestController
public class BlogController {

	private static final Logger logger = 
			LoggerFactory.getLogger(BlogController.class);
	

	
	@Autowired
	private BlogDAO blogDAO;
	
	@Autowired
	private Blog blog;
	
		
	@GetMapping("/blogs")
	public List<Blog> getBlogs() {
		logger.debug("calling method getBlogs");
		
		List blogs =   blogDAO.list();
		
		if(blogs==null)
			
		{
			blog = new Blog();
			
			blog.setErrorCode("404");
			blog.setErrorMessage("No blog s are available");
			blogs.add(blog);
			
		}
		return blogs;
		
		
		//How it is returning JSONArray without proper return type i.e., ResponseEntity<List<Blog>>
	}

	@GetMapping("/blog/{id}")
	public Blog getBlog(@PathVariable("id") int id) {
		logger.debug("**************calling method getBlogs with the id " + id);
		Blog blog = blogDAO.get(id);
		if(blog==null)
		{
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("Blog not found with the id:" + id);
		}
		
		return blog;
		/*if (blog == null) {
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Blog>(blog, HttpStatus.OK);*/
	}

	@PostMapping(value = "/blog")
	public ResponseEntity<Blog> createBlog(@RequestBody Blog blog, HttpSession session) {
		logger.debug("calling method createBlog with id " +blog.getId());
		
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		logger.debug(" Blog is creating by the blog :"+loggedInUserID);
		blog.setUserID(loggedInUserID);
		blog.setStatus('N');// A->Accepted,  R->Rejected
		
		blogDAO.save(blog);

		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	

	@PutMapping("/blog/{id}")
	public ResponseEntity<Blog> updateBlog(@PathVariable int id, @RequestBody Blog blog) {
		logger.debug("calling method updateBlog with the id " + id);
		
		if (blogDAO.get(id)==null) {
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
		blogDAO.update(blog);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	

	@RequestMapping(value = "/accept_blog/{id}", method = RequestMethod.GET)
	public ResponseEntity<Blog> accept(@PathVariable("id") int id) {
		logger.debug("Starting of the method accept");

		blog = updateStatus(id, 'A', "");
		logger.debug("Ending of the method accept");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);

	}

	@RequestMapping(value = "/reject_blog/{id}/{reason}", method = RequestMethod.GET)
	public ResponseEntity<Blog> reject(@PathVariable("id") int id, @PathVariable("reason") String reason) {
		logger.debug("Starting of the method reject");

		blog = updateStatus(id, 'R', reason);
		logger.debug("Ending of the method reject");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);

	}

	private Blog updateStatus(int id, char status, String reason) {
		logger.debug("Starting of the method updateStatus");

		logger.debug("status: " + status);
		blog = blogDAO.get(id);

		if (blog == null) {
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("Could not update the status");
		} else {

			blog.setStatus(status);
			blog.setReason(reason);
			
			blogDAO.update(blog);
			
			blog.setErrorCode("200");
			blog.setErrorMessage("Updated the status successfully");
		}
		logger.debug("Ending of the method updateStatus");
		return blog;

	}
}


