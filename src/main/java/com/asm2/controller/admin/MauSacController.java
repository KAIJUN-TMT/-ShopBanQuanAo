package com.asm2.controller.admin;


import com.asm2.model.mauSac;
import com.asm2.repository.mauSacRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quanly")
public class MauSacController {
    
	
	@GetMapping("/sanpham/mausac")
	public String danhSach(Model model) {
	    model.addAttribute("mauSac", new mauSac());  // đảm bảo có object để binding
	    model.addAttribute("dsMauSac", mauSacRepository.findAll());
	    return "/admin/sanpham/mausac";
	}

    @Autowired
    private mauSacRepository mauSacRepository;

    @PostMapping("/sanpham/mausac/luu")
    public String save(@ModelAttribute("mauSac") mauSac mau, Model model) {
        // Mặc định trạng thái nếu null
        if (mau.getTrangThai() == null) {
            mau.setTrangThai(true);
        }

        // Kiểm tra bỏ trống
        boolean isEmpty = mau.getTenMauSac() == null || mau.getTenMauSac().trim().isEmpty();
        if (isEmpty) {
            model.addAttribute("dsMauSac", mauSacRepository.findAll());
            model.addAttribute("tenbotrong", "Tên màu sắc không được bỏ trống");
            return "/admin/sanpham/mausac";
        }

        // Kiểm tra trùng tên
        boolean isDuplicate = false;
        if (mau.getId() == null || mau.getId() == 0) {
            // Thêm mới
            isDuplicate = mauSacRepository.existsByTenMauSacIgnoreCase(mau.getTenMauSac());
        } else {
            // Sửa
            mauSac existing = mauSacRepository.findByTenMauSac(mau.getTenMauSac());
            if (existing != null && !existing.getId().equals(mau.getId())) {
                isDuplicate = true;
            }
        }

        if (isDuplicate) {
            model.addAttribute("dsMauSac", mauSacRepository.findAll());
            model.addAttribute("error", "Tên màu đã tồn tại");
            return "/admin/sanpham/mausac";
        }

        // Lưu vào DB
        mauSacRepository.save(mau);
        return "redirect:/quanly/sanpham/mausac";
    }


    @GetMapping("/sanpham/mausac/sua/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        mauSac ms = mauSacRepository.findById(id).orElse(new mauSac());
        model.addAttribute("mauSac", ms);
        model.addAttribute("dsMauSac", mauSacRepository.findAll());
        return "/admin/sanpham/mausac";
    }
    @GetMapping("/sanpham/mausac/xoa/{id}")
    public String delete(@PathVariable("id") Integer id) {
        mauSacRepository.deleteById(id);
        return "redirect:/quanly/sanpham/mausac";
    }

    @PostMapping("/sanpham/mausac/tim")
    public String timMauSac(@RequestParam("keyword") String keyword, Model model) {
        List<mauSac> danhSachMau = mauSacRepository.findByTenMauSacContainingIgnoreCase(keyword);
        model.addAttribute("dsMauSac", danhSachMau);
        model.addAttribute("mauSac", new mauSac());  // để dùng cho form thêm nếu cần
        return "admin/sanpham/mausac";
    }

}
