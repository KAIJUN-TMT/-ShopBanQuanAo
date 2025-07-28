package com.asm2.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asm2.model.taiKhoan;
import com.asm2.repository.taiKhoanRepository;

@Controller
@RequestMapping("/quanly/nguoidung")
public class NguoiDungController {

    @Autowired
    private taiKhoanRepository taikhoanRepository;

    @GetMapping
    public String showUsers(Model model) {
        List<taiKhoan> users = taikhoanRepository.findAll();  
        model.addAttribute("users", users);
        return "admin/qlnguoidung"; 
    }
    
    @GetMapping("/khoa/{id}")
    public String khoaMoTaiKhoan(@PathVariable("id") Integer id) {
        Optional<taiKhoan> optTaiKhoan = taikhoanRepository.findById(id);
        if (optTaiKhoan.isPresent()) {
            taiKhoan taikhoan = optTaiKhoan.get();
            taikhoan.setTrangThai(!taikhoan.getTrangThai()); 
            taikhoanRepository.save(taikhoan); 
        }
        return "redirect:/quanly/nguoidung";
    }
}