package com.asm2.controller.admin;

import com.asm2.model.loaiSanPham;
import com.asm2.model.mauSac;
import com.asm2.model.thuongHieu;
import com.asm2.repository.loaiSanPhamRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/quanly")
public class LoaiSanPhamController {

	@GetMapping("/sanpham/loai")
	public String loai(Model model) {
	    model.addAttribute("loaiSanPham", new loaiSanPham());
	    model.addAttribute("dsLoai", repo.findAll());      
	    return "/admin/sanpham/loai";
	}
	
	@GetMapping("/sanpham/loai/lammoi")
	public String lammoi(Model model) {
	    
	    model.addAttribute("dsLoai", repo.findAll());      
	    return "/admin/sanpham/loai";
	}
	
    @Autowired
    private loaiSanPhamRepository repo;

  
    @GetMapping("/sanpham/loai/them")
    public String showForm(Model model) {
        model.addAttribute("loaiSanPham", new loaiSanPham());
        return "/admin/sanpham/loai";
    }

    @PostMapping("/sanpham/loai/luu")
    public String save(@ModelAttribute loaiSanPham loaiSanPham, Model model) {
        boolean hasError = false;

        if (loaiSanPham.getTenLoaiSanPham() == null || loaiSanPham.getTenLoaiSanPham().trim().isEmpty()) {
            hasError = true;
        }

        if (hasError) {
            model.addAttribute("dsLoai", repo.findAll());
            model.addAttribute("tenbotrong", "Tên Loại không được bỏ trống");
            return "/admin/sanpham/loai";
        }

        boolean isDuplicate = false;

        if (loaiSanPham.getId() == null) {
            // Thêm mới
            isDuplicate = repo.existsByTenLoaiSanPhamIgnoreCase(loaiSanPham.getTenLoaiSanPham());
        } else {
            // Cập nhật
            loaiSanPham existing = repo.findByTenLoaiSanPham(loaiSanPham.getTenLoaiSanPham());
            if (existing != null && !existing.getId().equals(loaiSanPham.getId())) {
                isDuplicate = true;
            }
        }

        if (isDuplicate) {
            model.addAttribute("dsLoai", repo.findAll());
            model.addAttribute("error", "Tên Loại đã tồn tại");
            return "/admin/sanpham/loai";
        }

        if (loaiSanPham.getTrangThai() == null) {
            loaiSanPham.setTrangThai(true);
        }

        repo.save(loaiSanPham);
        return "redirect:/quanly/sanpham/loai";
    }
    @GetMapping("/sanpham/loai/xoa/{id}")
    public String delete(@PathVariable("id") Integer id) {
        repo.deleteById(id);
        return "redirect:/quanly/sanpham/loai";
    }

    @GetMapping("/sanpham/loai/sua/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        loaiSanPham loai = repo.findById(id).orElse(new loaiSanPham());
        model.addAttribute("loaiSanPham", loai);
        model.addAttribute("dsLoai", repo.findAll());
        return "/admin/sanpham/loai";
    }

    @PostMapping("/sanpham/loai/tim")
    public String search(@RequestParam("keyword") String keyword, Model model) {
    	  List<loaiSanPham> danhSachLoai = repo.findByTenLoaiSanPhamContainingIgnoreCase(keyword);
          model.addAttribute("dsLoai", danhSachLoai);
          model.addAttribute("loaiSanPham", new loaiSanPham());
          return "admin/sanpham/loai";
    }
}

    
