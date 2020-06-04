package com.example.demo.login.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
=======
import org.springframework.beans.factory.annotation.Qualifier;
>>>>>>> origin/master
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.RestService;

@RestController
public class UserRestController {
	
	@Autowired
<<<<<<< HEAD
=======
	@Qualifier("RestServiceMybatisImpl")
>>>>>>> origin/master
	RestService service;
	
	@GetMapping("/rest/get")
	public List<User> getUserMany() {
		return service.selectMany();
	}
	
	@GetMapping("/rest/get/{id:.+}")
	public User getUserOne(@PathVariable("id") String userId) {
		return service.selectOne(userId);
	}
	
	@PostMapping("/rest/insert")
	public String postUserOne(@RequestBody User user) {
		boolean result = service.insert(user);
		
		String str = "";
		
		if (result == true)
			str = "{\"result\":\"ok\"}";
		else
			str = "{\"result\":\"error\"}";
		
		return str;
	}
	
	@PutMapping("/rest/update")
	public String putUserOne(@RequestBody User user) {
<<<<<<< HEAD
		boolean result = service.update(user);
=======
		boolean result = service.updateOne(user);
>>>>>>> origin/master
		
		String str = "";
		
		if (result == true)
			str = "{\"result\":\"ok\"}";
		else
			str = "{\"result\":\"error\"}";
		
		return str;
	}
	
	@DeleteMapping("/rest/delete/{id:.+}")
	public String deleteUserOne(@PathVariable("id") String userId) {
<<<<<<< HEAD
		boolean result = service.delete(userId);
=======
		boolean result = service.deleteOne(userId);
>>>>>>> origin/master
		
		String str = "";
		
		if (result == true)
			str = "{\"result\":\"ok\"}";
		else
			str = "{\"result\":\"error\"}";
		
		return str;
	}
}
