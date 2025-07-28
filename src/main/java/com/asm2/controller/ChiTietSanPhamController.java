package com.asm2.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asm2.model.chiTietSanPham;
import com.asm2.model.sanPham;
import com.asm2.repository.chiTietSanPhamRepository;
import com.asm2.repository.sanPhamRepository;
import com.asm2.repository.thuongHieuRepository;
@Controller

public class ChiTietSanPhamController {

	 @Autowired
	 private chiTietSanPhamRepository chiTietSanPhamRepository;
	   @Autowired
	    private thuongHieuRepository thuongHieuRepository;
	   
	 @Autowired
	 private sanPhamRepository sanPhamRepository;

	 @GetMapping("/chitietsanpham/{id}")
	 public String chiTietSanPham(@PathVariable("id") Integer id, Model model) {
	     sanPham sp = sanPhamRepository.findById(id).orElse(null);
	     if (sp == null) {
	         return "redirect:/404";
	     }

	     List<chiTietSanPham> chiTietList = sp.getChiTietSanPham();

	    
	     List<chiTietSanPham> kichThuocKhacNhau = new ArrayList<>();
	     Set<String> kichThuocDaCo = new HashSet<>();

	     for (chiTietSanPham ct : chiTietList) {
	         String tenSize = ct.getKichThuoc().getTenKichThuoc();
	         if (!kichThuocDaCo.contains(tenSize)) {
	             kichThuocKhacNhau.add(ct);
	             kichThuocDaCo.add(tenSize);
	         }
	     }

	    
	     List<chiTietSanPham> mauSacKhacNhau = new ArrayList<>();
	     Set<String> mauSacDaCo = new HashSet<>();

	     for (chiTietSanPham ct : chiTietList) {
	         String tenMau = ct.getMauSac().getTenMauSac(); // lấy tên màu
	         if (!mauSacDaCo.contains(tenMau)) {
	             mauSacKhacNhau.add(ct);
	             mauSacDaCo.add(tenMau);
	         }
	     }

	     model.addAttribute("kichThuocKhacNhau", kichThuocKhacNhau);
	     model.addAttribute("mauSacKhacNhau", mauSacKhacNhau); // ✅ thêm dòng này
	     model.addAttribute("chiTietDuocChon", chiTietList.get(0));
	     model.addAttribute("sanPham", sp);
	     model.addAttribute("danhSachChiTiet", chiTietList);

	     return "client/chitietsanpham";
	 }


}
