package com.asm2.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asm2.service.ThongKeService;

@Controller
@RequestMapping("/quanly")
public class ThongKeController {
	@Autowired
	ThongKeService thongKeService;
	
	@GetMapping("/thongke")
	public String thongkeForm(Model model) {
		List<Map<String, Object>> topNguoiMua = thongKeService.layKhachHangMuaNhieuNhat(5);
	    List<Map<String, Object>> topSanPham = thongKeService.layTopSanPhamBanChay(5);
	    List<Map<String, Object>> thongKeNgay = thongKeService.layThongKeDonHangTheoNgay();

	    model.addAttribute("topNguoiMua", topNguoiMua);
	    model.addAttribute("topSanPham", topSanPham);
	    model.addAttribute("thongKeNgay", thongKeNgay); // mới thêm

	    return "admin/qlthongke";
	}
}
