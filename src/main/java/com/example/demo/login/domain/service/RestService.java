package com.example.demo.login.domain.service;

import java.util.List;
import com.example.demo.login.domain.model.User;

public interface RestService {
	public boolean insert(User user);
	
	public User selectOne(String userId);
	
	public List<User> selectMany();
	
<<<<<<< HEAD
	public boolean update(User user);
	
	public boolean delete(String userId);
=======
	public boolean updateOne(User user);
	
	public boolean deleteOne(String userId);
>>>>>>> origin/master
}
