package com.example.demo.login.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.login.domain.model.User;

@Mapper
public interface UserMapper {
	@Insert("INSERT INTO m_user("
			+ " employee_id,"
			+ " password,"
			+ " user_name,"
			+ " birthday,"
			+ " age,"
			+ " marriage,"
			+ " role)"
			+ " VALUES ("
			+ " #{employeeId},"
			+ " #{password},"
			+ " #{userName},"
			+ " #{birthday},"
			+ " #{age},"
			+ " #{marriage},"
			+ " #{role})")
	public boolean insert(User user);
	
	@Select("SELECT employee_id AS employeeId,"
			+ "password,"
			+ "user_name AS userName,"
			+ "birthday,"
			+ "age,"
			+ "marriage,"
			+ "role"
			+ " FROM m_user"
			+ " WHERE employee_id = #{employeeId}")
	public User selectOne(String userId);
	
	@Select("SELECT employee_id AS employeeId,"
			+ "password,"
			+ "user_name AS userName,"
			+ "birthday,"
			+ "age,"
			+ "marriage,"
			+ "role"
			+ " FROM m_user")
	public List<User> selectMany();
	
	@Update("UPDATE m_user SET"
			+ " password = #{password},"
			+ " user_name = #{userName},"
			+ " birthday = #{birthday},"
			+ " age = #{age},"
			+ " marriage = #{marriage},"
			+ " WHERE employee_id = #{employeeId}")
	public boolean updateOne(User user);
	
	@Delete("DELETE FROM m_user WHERE employee_id = #{employeeId}")
	public boolean deleteOne(String userId);
}
