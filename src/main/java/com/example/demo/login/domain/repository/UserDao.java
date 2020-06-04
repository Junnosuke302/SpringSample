package com.example.demo.login.domain.repository;

import java.util.List;
import org.springframework.dao.DataAccessException;
import com.example.demo.login.domain.model.User;

public interface UserDao {
	public int count() throws DataAccessException;
	
	public int insertOne(User user) throws DataAccessException;
	
<<<<<<< HEAD
	public User selectOne(String userId) throws DataAccessException;
=======
	public User selectOne(String employeeId) throws DataAccessException;
>>>>>>> origin/master
	
	public List<User> selectMany() throws DataAccessException;
	
	public int updateOne(User user) throws DataAccessException;
	
<<<<<<< HEAD
	public int deleteOne(String userId) throws DataAccessException;
=======
	public int deleteOne(String employeeId) throws DataAccessException;
>>>>>>> origin/master
	
	public void userCsvOut() throws DataAccessException;
}
