package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository("UserDaoNamedJdbcImpl")
public class UserDaoNamedJdbcImpl implements UserDao {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	PasswordEncoder passwordEncode;
	
	@Override
	public int count() {
		String sql = "SELECT COUNT(*) FROM m_user";
		
		SqlParameterSource params = new MapSqlParameterSource();
		
		return jdbc.queryForObject(sql, params, Integer.class);
	}
	
	@Override
	public int insertOne(User user) {
		String password = passwordEncode.encode(user.getPassword());
		
		String sql = "INSERT INTO m_user(employee_id,"
				+ " password,"
				+ " user_name,"
				+ " birthday,"
				+ " age,"
				+ " marriage,"
				+ " role)"
				+ " VALUES(:employeeId,"
				+ " :password,"
				+ " :userName,"
				+ " :birthday,"
				+ " :age,"
				+ " :marriage,"
				+ " :role)";
		
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("employeeId", user.getEmployeeId())
				.addValue("password", password)
				.addValue("userName", user.getUserName())
				.addValue("birthday", user.getBirthday())
				.addValue("age", user.getAge())
				.addValue("marriage", user.isMarriage())
				.addValue("role", user.getRole());
		
		return jdbc.update(sql, params);
	}
	
	@Override
	public User selectOne(String employeeId) {
		String sql = "SELECT * FROM m_user WHERE employee_id = :employeeId";
		
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("employeeId", employeeId);
		
		Map<String, Object> map = jdbc.queryForMap(sql, params);
		
		User user = new User();
		
		user.setEmployeeId((String)map.get("employee_id"));
		user.setPassword((String)map.get("passowrd"));
		user.setUserName((String)map.get("user_name"));
		user.setBirthday((Date)map.get("birthday"));
		user.setAge((Integer)map.get("age"));
		user.setMarriage((Boolean)map.get("marriage"));
		user.setRole((String)map.get("role"));
		
		return user;
	}
	
	@Override
	public List<User> selectMany() {
		String sql = "SELECT * FROM m_user";
		
		SqlParameterSource params = new MapSqlParameterSource();
		
		List<Map<String, Object>> getList = jdbc.queryForList(sql, params);
		
		List<User> userList = new ArrayList<>();
		
		for (Map<String, Object> map: getList) {
			User user = new User();
			
			user.setEmployeeId((String)map.get("employee_id"));
			user.setPassword((String)map.get("passowrd"));
			user.setUserName((String)map.get("user_name"));
			user.setBirthday((Date)map.get("birthday"));
			user.setAge((Integer)map.get("age"));
			user.setMarriage((Boolean)map.get("marriage"));
			user.setRole((String)map.get("role"));
			
			userList.add(user);
		}
		
		return userList;
	}
	
	@Override
	public int updateOne(User user) {
		String password = passwordEncode.encode(user.getPassword());
		
		String sql = "UPDATE m_user"
				+ " SET"
				+ " password = :password,"
				+ " user_name = :userName,"
				+ " birthday = :birthday,"
				+ " age = :age,"
				+ " marriage = :marriage"
				+ " WHERE employee_id = :employeeId";
		
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userId", user.getEmployeeId())
				.addValue("password", password)
				.addValue("userName", user.getUserName())
				.addValue("birthday", user.getBirthday())
				.addValue("age", user.getAge())
				.addValue("marriage", user.isMarriage());
		
		return jdbc.update(sql, params);
	}
	
	@Override
	public int deleteOne(String employeeId) {
		String sql = "DELETE FROM m_user WHERE employee_id = :employeeId";
		
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("employeeId", employeeId);
		
		int rowNumber = jdbc.update(sql, params);
		
		return rowNumber;
	}
	
	@Override
	public void userCsvOut() {
		String sql = "SELECT * FROM m_user";
		
		UserRowCallbackHandler handler = new UserRowCallbackHandler();
		
		jdbc.query(sql, handler);
	}
}
