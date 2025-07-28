package com.asm2.controller.admin;

import com.asm2.model.loaiSanPham;
import com.asm2.model.mauSac;
import com.asm2.model.thuongHieu;
import com.asm2.repository.thuongHieuRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quanly")
public class ThuongHieuController {

    @Autowired
    private thuongHieuRepository repo;

    @GetMapping("/sanpham/thuonghieu")
    public String thuongHieu(Model model) {
        model.addAttribute("thuongHieu", new thuongHieu());
        model.addAttribute("dsThuongHieu", repo.findAll());
        return "/admin/sanpham/thuonghieu";
    }
    @GetMapping("/sanpham/thuonghieu/them")
    public String thuongHeu(Model model) {
        List<thuongHieu> danhSachThuongHieu = repo.findAll();
        model.addAttribute("danhSachThuongHieu", danhSachThuongHieu);
        model.addAttribute("thuonghieumoi", new thuongHieu());
        return "admin/sanpham/thuongHieu"; 
    }

    @PostMapping("/sanpham/thuonghieu/luu")
    public String luuThuongHieu(@Valid @ModelAttribute thuongHieu thuongHieu,
                                BindingResult result,
                                Model model) {

       
    	boolean Check = false;
    	if (thuongHieu.getTenThuongHieu() == null || thuongHieu.getTenThuongHieu().trim().isEmpty()) {
       	 
        	Check = true;
        }
        
    	 if (Check) {
             model.addAttribute("dsThuongHieu", repo.findAll());
             model.addAttribute("tenbotrong", "Tên thương hiệu không được bỏ trống");
             return "/admin/sanpham/thuonghieu";
         }

        
        boolean isDuplicate = false;
        if (thuongHieu.getId() == 0) {
            isDuplicate = repo.existsByTenThuongHieuIgnoreCase(thuongHieu.getTenThuongHieu());
        } else {
            
            thuongHieu existing = repo.findByTenThuongHieu(thuongHieu.getTenThuongHieu());
            if (existing != null && existing.getId() != thuongHieu.getId()) {
                isDuplicate = true;
            }
        }
        
        
        if (isDuplicate) {
            model.addAttribute("dsThuongHieu", repo.findAll());
            model.addAttribute("error", "Tên thương hiệu đã tồn tại");
            return "/admin/sanpham/thuonghieu";
        }

        // Nếu trạng thái null (chưa chọn), mặc định true
        if (thuongHieu.getTrangThai() == null) {
            thuongHieu.setTrangThai(true);
        }
        
        repo.save(thuongHieu);
        return "redirect:/quanly/sanpham/thuonghieu";
    }


    @GetMapping("/sanpham/thuonghieu/xoa/{id}")
    public String delete(@PathVariable("id") Integer id) {
        repo.deleteById(id);
        return "redirect:/quanly/sanpham/thuonghieu";
    }

    @GetMapping("/sanpham/thuonghieu/sua/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        thuongHieu thuongHieune = repo.findById(id).orElse(new thuongHieu());
        model.addAttribute("thuongHieu", thuongHieune);
        model.addAttribute("dsThuongHieu", repo.findAll());
        return "/admin/sanpham/thuonghieu";
    }


    @PostMapping("/sanpham/thuonghieu/tim")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<thuongHieu> danhSachThuongHieu = repo.findByTenThuongHieuContainingIgnoreCase(keyword);
        model.addAttribute("dsThuongHieu", danhSachThuongHieu);
        model.addAttribute("thuongHieu", new thuongHieu());
        return "/admin/sanpham/thuonghieu";
    }
}
