package com.example.demo.login.domain.repository.jdbc;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//カウント queryForObject
	@Override
	public int count() throws DataAccessException {
		int count = jdbc.queryForObject("SELECT COUNT(*) FROM m_user", Integer.class);
		
		return count;
	}
	
	//登録 update
	@Override
	public int insertOne(User user) throws DataAccessException {
		
		String password = passwordEncoder.encode(user.getPassword());
		
		String sql = "INSERT INTO m_user("
				+ " user_id,"
				+ " password,"
				+ " user_name,"
				+ " birthday,"
				+ " age,"
				+ " marriage,"
				+ " role)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?)";
				
		int rowNumber = jdbc.update(sql,
				user.getUserId(),
				password,
				user.getUserName(),
				user.getBirthday(),
				user.getAge(),
				user.isMarriage(),
				user.getRole());
		
		return rowNumber;
	}
	
	//１件表示(詳細表示) queryForMap
	@Override
	public User selectOne(String userId) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM m_user WHERE user_id = ?", userId);
		
		User user = new User();
		
		user.setUserId((String)map.get("user_id"));
		user.setPassword((String)map.get("password"));
		user.setUserName((String)map.get("user_name"));
		user.setBirthday((Date)map.get("birthday"));
		user.setAge((Integer)map.get("age"));
		user.setMarriage((boolean)map.get("marriage"));
		user.setRole((String)map.get("role"));
		
		return user;
	}
	
	//全件表示 queryForList
	@Override
	public List<User> selectMany() throws DataAccessException {
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM m_user");
		
		List<User> userList = new ArrayList<>();
		
		for (Map<String, Object> map: getList) {
			User user = new User();
			
			user.setUserId((String)map.get("user_id"));
			user.setPassword((String)map.get("password"));
			user.setUserName((String)map.get("user_name"));
			user.setBirthday((Date)map.get("birthday"));
			user.setAge((Integer)map.get("age"));
			user.setMarriage((boolean)map.get("marriage"));
			user.setRole((String)map.get("role"));
			
			userList.add(user);
		}
		
		return userList;
	}
	
	//１件更新 update
	@Override
	public int updateOne(User user) throws DataAccessException {
		
		String password = passwordEncoder.encode(user.getPassword());
		
		String sql = "UPDATE m_user SET"
				+ " password = ?,"
				+ " user_name = ?,"
				+ " birthday = ?,"
				+ " age = ?,"
				+ " marriage = ?"
				+ " WHERE user_id = ?";
		
		int rowNumber = jdbc.update(sql,
				password,
				user.getUserName(),
				user.getBirthday(),
				user.getAge(),
				user.isMarriage(),
				user.getUserId());
		
		/*if (rowNumber > 0) {
			throw new DataAccessException("トランザクションテスト") {};
		}*/
		
		return rowNumber;
	}
	
	//１件削除 update
	@Override
	public int deleteOne(String userId) throws DataAccessException {
		int rowNumber = jdbc.update("DELETE FROM m_user WHERE user_id = ?", userId);
		
		return rowNumber;
	}
	
	//CSV出力
	@Override
	public void userCsvOut() throws DataAccessException {
		String sql = "SELECT * FROM m_user";
		
		UserRowCallbackHandler handler = new UserRowCallbackHandler();
		
		jdbc.query(sql, handler);
	}
}
