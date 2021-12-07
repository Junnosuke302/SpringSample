package com.example.demo.login.domain.service;

import java.util.List;
import com.example.demo.login.domain.model.User;

public interface RestService {
	public boolean insert(User user);
	
	public User selectOne(String userId);
	
	public List<User> selectMany();
	
	public boolean updateOne(User user);
	
	public boolean deleteOne(String userId);
}
