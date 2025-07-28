package com.asm2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asm2.model.sanPham;
import com.asm2.repository.chiTietSanPhamRepository;
import com.asm2.repository.loaiSanPhamRepository;
import com.asm2.repository.sanPhamRepository;
import com.asm2.repository.thuongHieuRepository;
@Controller
public class HomeController {
	@Autowired
    private sanPhamRepository sanPhamRepository;

    @Autowired
    private loaiSanPhamRepository loaiSanPhamRepository;

    @Autowired
    private thuongHieuRepository thuongHieuRepository;
    
    @Autowired
    private chiTietSanPhamRepository chiTietSanPhamRepository;
    
	@RequestMapping("/trangchu")
	public String trangchu(Model model) {
		
		List<sanPham> dsSanPham = sanPhamRepository.findAll();
        model.addAttribute("dsSanPham", dsSanPham);
       
		return ("/client/trangchu");
	}
	
	@RequestMapping("/sanpham")
	public String sanpham() {
		return ("/client/sanpham");
	}
	
	@RequestMapping("/gioithieu")
	public String gioithieu() {
		return ("/client/gioithieu");
	}
	
	@RequestMapping("/hotro")
	public String hotro() {
		return ("/client/hotro");
	}
	
	@RequestMapping("/thongtintaikhoan")
	public String thongtintaikhoan() {
		return ("/client/thongtintaikhoan");
	}
	
	@RequestMapping("/giohang")
	public String giohang() {
		return ("/client/giohang");
	}
	
	@RequestMapping("/thanhtoan")
	public String thanhtoan() {
		return ("/client/thanhtoan");
	}
}
