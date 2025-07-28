package com.asm2.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/quanly")
public class TongQuanController {

	@RequestMapping("/tongquan")
	public String tongquan() {
		return ("/admin/tongquan");
	}

}
