package com.example.demo.login.domain.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Attend {
	private String employeeId;
	private String userName;
	private LocalDate workDay;
	private String startWork;
	private String endWork;
	private String lateness;
	private String leaveEarly;
	private String overtime;
	private String workingTime;
	private String status;
	private String remarks;
}
