package com.example.demo.login.domain.repository.jdbc;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.example.demo.login.domain.model.Attend;
import com.example.demo.login.domain.repository.AttendDao;

@Repository("AttendDaoJdbcImpl")
public class AttendDaoJdbcImpl implements AttendDao {
	
	@Autowired
	private JdbcTemplate jdbc;
	
	//時間変換
	private String convert(int hour, int min) {
		String time;
		
		if (hour < 10)
			time = String.valueOf("0" + hour + ":");
		else
			time = String.valueOf(hour + ":");

		if (min < 10)
			time += String.valueOf("0" + min);
		else
			time += String.valueOf(min);
		
		return time;
	}
	
	@Override
	public List<Attend> selectMany() throws DataAccessException {
		String sql = "SELECT * FROM attendance_management ORDER BY work_day ASC";
		
		RowMapper<Attend> rowMapper = new BeanPropertyRowMapper<Attend>(Attend.class);
		
		return jdbc.query(sql, rowMapper);
	}
	
	@Override
	public List<Attend> selectEmployee(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String employeeId = auth.getName();
		
		String sql = "SELECT * FROM attendance_management WHERE employee_id = ? ORDER BY work_day ASC";
		
		RowMapper<Attend> rowMapper = new BeanPropertyRowMapper<Attend>(Attend.class);
		
		return jdbc.query(sql, rowMapper, employeeId);
	}
	
	@Override
	public boolean checkingAttend(Model model) throws DataAccessException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String employeeId = auth.getName();
		
		String sql = "SELECT work_day FROM attendance_management WHERE employee_id = ?";
		
		RowMapper<Attend> rowMapper = new BeanPropertyRowMapper<Attend>(Attend.class);
		
		List<Attend> checkList = jdbc.query(sql, rowMapper, employeeId);
		LocalDate today = LocalDate.now();
		
		for (Attend List : checkList) {
			if (List.getWorkDay().equals(today))
				return false;
		}
		
		return true;
	}
	
	@Override
	public boolean checkingAttendLate(Model model) throws DataAccessException {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		
		if (hour >= 9 && min > 0)
			return this.checkingAttend(model);
		
		return false;
	}
	
	@Override
	public int attendStart(Model model, String remarks) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String employeeId = auth.getName();
		
		//出勤時間取得
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		
		//遅刻時間
		int lateHour = 0;
		int lateMin = 0;

		//遅刻
		if (hour >= 9 && min > 0) {
			if (min > 45) {
				lateHour = hour - 9 + 1;
			} else {
				lateHour = hour - 9;
				if (min > 30)
					lateMin = 45;
				else if (min > 15)
					lateMin = 30;
				else
					lateMin = 15;
			}
		}
		
		Map<String, Object> map = jdbc.queryForMap("SELECT employee_id, user_name FROM m_user WHERE employee_id = ?", employeeId);
		
		String sql = "INSERT INTO attendance_management ("
				+ " employee_id,"
				+ " user_name,"
				+ " work_day,"
				+ " start_work,"
				+ " end_work,"
				+ " lateness,"
				+ " leave_early,"
				+ " overtime,"
				+ " working_time,"
				+ " status,"
				+ " remarks)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		int rowNumber = jdbc.update(sql,
				map.get("employee_id"),
				map.get("user_name"),
				LocalDate.now(),
				convert(hour, min),
				null,
				convert(lateHour, lateMin),
				null,
				null,
				null,
				"出勤中",
				remarks);
		
		return rowNumber;
	}
	
	@Override
	public int attendEnd(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String employeeId = auth.getName();
		LocalDate today = LocalDate.now();
		
		//退勤時間
		Calendar cal = Calendar.getInstance();
		int endHour = cal.get(Calendar.HOUR_OF_DAY);
		int endMin = cal.get(Calendar.MINUTE);
		
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM attendance_management WHERE employee_id = ? AND work_day = ?", employeeId, today);
		
		//出勤時間取得
		String startTime = (String)map.get("start_work");
		int startHour = Integer.parseInt(startTime.substring(0, 2));
		int startMin = Integer.parseInt(startTime.substring(3, 5));
		
		//早退
		int leaveEarlyHour = 0;
		int leaveEarlyMin = 0;
		if (endHour < 18) {
			leaveEarlyHour = 18 - endHour;
			if (60 - endMin > 0) {
				leaveEarlyHour--;
				leaveEarlyMin = 60 - endMin;
			} else {
				leaveEarlyMin = 0;
			}
			
			if (leaveEarlyMin < 15) {
				leaveEarlyMin = 15;
			} else if (leaveEarlyMin < 30) {
				leaveEarlyMin = 30;
			} else if (leaveEarlyMin < 45) {
				leaveEarlyMin = 45;
			} else {
				leaveEarlyHour++;
				leaveEarlyMin = 0;
			}
		}
		
		//残業
		int overtimeHour = 0;
		int overtimeMin = 0;
		if (endHour >= 18) {
			overtimeHour = endHour - 18;
			if (endMin >= 45)
				overtimeMin = 45;
			else if (endMin >= 30)
				overtimeMin = 30;
			else if (endMin >= 15)
				overtimeMin = 15;
			else
				overtimeMin = 0;
		}
		
		//実働
		int workingHour = 0;
		int workingMin = 0;
		workingHour = endHour - startHour;
		if (endMin - startMin <  0) {
			workingHour--;
			workingMin = 60 - endMin - startMin;
		}
		//休憩1時間
		if (workingHour > 8)
			workingHour--;
		
		if (workingMin >= 45)
			workingMin = 45;
		else if (workingMin >= 30)
			workingMin = 30;
		else if (workingMin >= 15)
			workingMin = 15;
		else
			workingMin = 0;
		
		String sql = "UPDATE attendance_management SET"
				+ " user_name = ?,"
				+ " start_work = ?,"
				+ " end_work = ?,"
				+ " lateness = ?,"
				+ " leave_early = ?,"
				+ " overtime = ?,"
				+ " working_time = ?,"
				+ " status = ?,"
				+ " remarks = ?"
				+ " WHERE"
				+ " employee_id = ? AND"
				+ " work_day = ?";
		
		int rowNumber = jdbc.update(sql,
				map.get("user_name"),
				startTime,
				convert(endHour, endMin),
				map.get("lateness"),
				convert(leaveEarlyHour, leaveEarlyMin),
				convert(overtimeHour, overtimeMin),
				convert(workingHour, workingMin),
				"退社",
				map.get("remarks"),
				employeeId,
				today);
		
		return rowNumber;
	}
}
