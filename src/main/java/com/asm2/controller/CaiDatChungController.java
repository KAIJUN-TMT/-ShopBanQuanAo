package com.asm2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/quanly")
public class CaiDatChungController {
	@GetMapping("/caidatchung")
	public String catdatchungForm() {
		return "admin/caidatchung";
	}
	
	 @PostMapping("/save-settings")
	 public String saveSettings(@RequestParam("mode") String mode, HttpSession session) {
		 session.setAttribute("theme", "light"); // Lưu chế độ sáng/tối vào session
		 return "admin/caidatchung";
	 }
}
