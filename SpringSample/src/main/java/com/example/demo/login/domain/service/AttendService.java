package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.example.demo.login.domain.model.Attend;
import com.example.demo.login.domain.repository.AttendDao;

@Transactional
@Service
public class AttendService {
	
	@Autowired
	AttendDao dao;
	
	public List<Attend> selectMany() {
		return dao.selectMany();
	}
	
	public List<Attend> selectEmployee(Model model) {
		return dao.selectEmployee(model);
	}
	
	public boolean checkingAttend(Model model) {
		return dao.checkingAttend(model);
	}
	
	public boolean checkingAttendLate(Model model) {
		return dao.checkingAttendLate(model);
	}
	
	public boolean attendStart(Model model, String remarks) {
		int rowNumber = dao.attendStart(model, remarks);
		
		boolean result = false;
		
		if (rowNumber > 0)
			result = true;
		
		return result;
	}
	
	public boolean attendEnd(Model model) {
		int rowNumber = dao.attendEnd(model);
		
		boolean result = false;
		
		if (rowNumber > 0)
			result = true;
		
		return result;
	}
}
