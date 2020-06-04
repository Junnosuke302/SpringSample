package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;

import com.example.demo.login.domain.model.Attend;

public interface AttendDao {
	public List<Attend> selectMany() throws DataAccessException;
	
	public List<Attend> selectEmployee(Model model) throws DataAccessException;
	
	public boolean checkingAttend(Model model) throws DataAccessException;
	
	public boolean checkingAttendLate(Model model) throws DataAccessException;
	
	public int attendStart(Model model, String remarks) throws DataAccessException;
	
	public int attendEnd(Model model) throws DataAccessException;
}
