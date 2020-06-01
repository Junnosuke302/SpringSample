package com.example.demo.login.domain.repository.jdbc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.example.demo.login.domain.model.Attend;

public class AttendExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Sheet sheet = workbook.createSheet("test");
		
		@SuppressWarnings("unchecked")
		List<Attend> attendList = (List<Attend>) model.get("attendList");
		
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("社員番号");
		cell= row.createCell(1);
		cell.setCellValue("氏名");
		cell = row.createCell(2);
		cell.setCellValue("出勤日");
		cell = row.createCell(3);
		cell.setCellValue("出社");
		cell = row.createCell(4);
		cell.setCellValue("退社");
		cell = row.createCell(5);
		cell.setCellValue("遅刻");
		cell = row.createCell(6);
		cell.setCellValue("早退");
		cell = row.createCell(7);
		cell.setCellValue("残業");
		cell = row.createCell(8);
		cell.setCellValue("実働");
		cell = row.createCell(9);
		cell.setCellValue("状況");
		cell = row.createCell(10);
		cell.setCellValue("備考");
		
		int i = 1;
		for (Attend attend : attendList) {
			row = sheet.createRow(i);
			cell = row.createCell(0);
			cell.setCellValue(attend.getEmployeeId());
			
			cell = row.createCell(1);
			cell.setCellValue(attend.getUserName());
			
			cell = row.createCell(2);
			LocalDate workDay = attend.getWorkDay();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			cell.setCellValue(workDay.format(dateTimeFormatter));
			
			cell = row.createCell(3);
			cell.setCellValue(attend.getStartWork());
			
			cell = row.createCell(4);
			cell.setCellValue(attend.getEndWork());
			
			cell = row.createCell(5);
			cell.setCellValue(attend.getLateness());
			
			cell = row.createCell(6);
			cell.setCellValue(attend.getLeaveEarly());
			
			cell = row.createCell(7);
			cell.setCellValue(attend.getOvertime());
			
			cell = row.createCell(8);
			cell.setCellValue(attend.getWorkingTime());
			
			cell = row.createCell(9);
			cell.setCellValue(attend.getStatus());
			
			cell = row.createCell(10);
			cell.setCellValue(attend.getRemarks());
			i++;
		}
	}

}
