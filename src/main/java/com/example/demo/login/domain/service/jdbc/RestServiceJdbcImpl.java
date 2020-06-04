package com.example.demo.login.domain.service.jdbc;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.RestService;
import com.example.demo.login.domain.repository.UserDao;

@Transactional
@Service
public class RestServiceJdbcImpl implements RestService {
	
	@Autowired
	@Qualifier("UserDaoJdbcImpl")
	UserDao dao;
	
	@Override
	public boolean insert(User user) {
		int result = dao.insertOne(user);
		
		if (result == 0)
			return false;
		else
			return true;
	}
	
	@Override
	public User selectOne(String userId) {
		return dao.selectOne(userId);
	}
	
	@Override
	public List<User> selectMany() {
		return dao.selectMany();
	}
	
	@Override
<<<<<<< HEAD
	public boolean update(User user) {
=======
	public boolean updateOne(User user) {
>>>>>>> origin/master
		int result = dao.updateOne(user);
		
		if (result == 0)
			return false;
		else
			return true;
	}
	
	@Override
<<<<<<< HEAD
	public boolean delete(String userId) {
=======
	public boolean deleteOne(String userId) {
>>>>>>> origin/master
		int result = dao.deleteOne(userId);
		
		if (result == 0)
			return false;
		else
			return true;
	}
}
