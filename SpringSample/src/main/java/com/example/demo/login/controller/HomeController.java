package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.login.domain.service.AttendService;
import com.example.demo.login.domain.service.UserService;
import java.io.IOException;
import java.util.*;

import com.example.demo.login.domain.model.Attend;
import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.jdbc.AttendExcelView;

@Controller
public class HomeController {
	
	@Autowired
	UserService userService;
	@Autowired
	AttendService attendService;
	
	private Map<String, String> radioMarriage;
	
	private Map<String, String> initRadioMarriage() {
		Map<String, String> radio = new LinkedHashMap<>();
		
		radio.put("既婚", "true");
		radio.put("未婚", "false");
		
		return radio;
	}
	
	@GetMapping("/home")
	public String getHome(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		
		model.addAttribute("contents", "login/home :: home_contents");
		
		//出勤してればfalse、ボタンを退勤に変える
		boolean attendStart, attendLate;
		attendStart = attendService.checkingAttend(model);
		attendLate = attendService.checkingAttendLate(model);
		
		//出社してなければtrue
		model.addAttribute("attendStart", attendStart);
		//遅刻してればtrue
		model.addAttribute("attendLate", attendLate);
		
		return "login/homeLayout";
	}
	
	@GetMapping("/attendList")
	public String getAttendList(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		
		model.addAttribute("contents", "login/attendList :: attendList_contents");
		
		List<Attend> attendList = attendService.selectMany();
		
		model.addAttribute("attendList", attendList);
		
		return "login/homeLayout";
	}
	
	@GetMapping("/userDetail/{id:.+}")
	public String getUserDetail(@ModelAttribute SignupForm form, Model model, @PathVariable("id") String userId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		
		System.out.println("userId = " + userId);
		
		model.addAttribute("contents", "login/userDetail :: userDetail_contents");
		
		radioMarriage = initRadioMarriage();
		
		model.addAttribute("radioMarriage", radioMarriage);
		
		if (userId != null && userId.length() > 0) {
			User user = userService.selectOne(userId);
			
			form.setEmployeeId(user.getEmployeeId());
			form.setUserName(user.getUserName());
			form.setBirthday(user.getBirthday());
			form.setAge(user.getAge());
			form.setMarriage(user.isMarriage());
			
			model.addAttribute("signupForm", form);
		}
		
		return "login/homeLayout";
	}
	
	@GetMapping("/individualAttendList")
	public String getInvidualAttendList(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		
		model.addAttribute("contents", "login/individualAttendList :: individualAttendList_contents");
		
		List<Attend> individualAttendList = attendService.selectEmployee(model);
		model.addAttribute("individualAttendList", individualAttendList);
		
		return "login/homeLayout";
	}
	
	@PostMapping(value = "/userDetail", params = "update")
	public String postUserDetailUpdate(@ModelAttribute @Validated(GroupOrder.class)SignupForm form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return getUserDetail(form, model, form.getEmployeeId());
		}
		
		System.out.println("更新ボタンの処理");
		
		User user = new User();
		
		user.setEmployeeId(form.getEmployeeId());
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setBirthday(form.getBirthday());
		user.setAge(form.getAge());
		user.setMarriage(form.isMarriage());
		
		
		boolean result = userService.updateOne(user);
		
		if (result == true)
			model.addAttribute("result", "更新成功");
		else
			model.addAttribute("result", "更新失敗");
		
		return getHome(model);
	}
	
	@PostMapping(value = "/userDetail", params = "delete")
	public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model) {
		System.out.println("削除ボタンの処理");
		
		boolean result = userService.deleteOne(form.getEmployeeId());
		
		if (result == true)
			model.addAttribute("result", "削除成功");
		else
			model.addAttribute("result", "削除失敗");
		
		return getHome(model);
	}
	
	@PostMapping(value = "/home", params = "start")
	public String postAttendStart(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		
		String remarks = null;
		
		boolean result = attendService.attendStart(model, remarks);
		
		if (result == true) {
			model.addAttribute("attendStart", false);
			model.addAttribute("attendLate", false);
			model.addAttribute("attendEnd", true);
			model.addAttribute("result", "出勤確認");
		} else {
			model.addAttribute("result", "出勤失敗");
		}
		
		return getHome(model);
	}
	
	@PostMapping(value = "home", params = "putRemarks")
	public String postAttendLate(@RequestParam("remarks")String remarks, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		
		boolean result = attendService.attendStart(model, remarks);
		
		if (result == true) {
			model.addAttribute("attendEnd", true);
			model.addAttribute("result", "送信確認");
		} else {
			model.addAttribute("result", "送信失敗");
		}
		
		return getHome(model);
	}
	
	@PostMapping(value = "/home", params = "end")
	public String postAttendEnd(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		
		boolean result = attendService.attendEnd(model);
		
		if (result == true) {
			model.addAttribute("attendEnd", false);
			model.addAttribute("result", "退勤確認");
		} else {
			model.addAttribute("result", "退勤失敗");
		}
		
		return getHome(model);
	}
	
	@GetMapping("/logout")
	public String getLogout() {
		
		return "redirect:/login";
	}
	
	@GetMapping("/userList/csv")
	public ResponseEntity<byte[]> getUserListCsv(Model model) {
		
		userService.userCsvOut();
		
		byte[] bytes = null;
		
		try {
			bytes = userService.getFile("sample.csv");
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "sample.csv");
		
		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/individualAttendList/excel")
	public AttendExcelView getAttendExcelList(Model model) {
		AttendExcelView aev = new AttendExcelView();
		List<Attend> attendList = attendService.selectMany();
		model.addAttribute("attendList", attendList);
		
		return aev;
	}
}
