package com.asm2.controller.admin;

import com.asm2.model.sanPham;
import com.asm2.repository.loaiSanPhamRepository;
import com.asm2.repository.sanPhamRepository;
import com.asm2.repository.thuongHieuRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/quanly")
public class SanPhamController {

    @Autowired
    private sanPhamRepository sanPhamRepository;

    @Autowired
    private loaiSanPhamRepository loaiSanPhamRepository;

    @Autowired
    private thuongHieuRepository thuongHieuRepository;

    @Autowired
    private HttpServletRequest request;

    // Hiển thị form thêm sản phẩm
    @GetMapping("/sanpham/themsanpham")
    public String hienThiFormThem(Model model) {
        model.addAttribute("sanPham", new sanPham());
        model.addAttribute("dsLoai", loaiSanPhamRepository.findAll());
        model.addAttribute("dsThuongHieu", thuongHieuRepository.findAll());
        return "admin/sanpham/themsanpham";
    }

    // Xử lý lưu sản phẩm
    @PostMapping("/sanpham/luu")
    public String luuSanPham(
            @Valid @ModelAttribute("sanPham") sanPham sanPham,
            BindingResult result,
            @RequestParam(name = "hinhAnhFile", required = false) MultipartFile photo,
            
            Model model
    ) {
    	
    	 
        // Kiểm tra hình ảnh trống
        if (photo == null || photo.isEmpty()) {
            result.rejectValue("hinhAnh", "error.sanPham", "Hình ảnh không được để trống");
        }

        if (result.hasErrors()) {
            
            
            result.getAllErrors().forEach(err -> System.out.println(" - " + err.getDefaultMessage()));

            model.addAttribute("dsLoai", loaiSanPhamRepository.findAll());
            model.addAttribute("dsThuongHieu", thuongHieuRepository.findAll());
            return "admin/sanpham/themsanpham";
        }

        try {
            // Lưu hình ảnh
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            String uploadDir = request.getServletContext().getRealPath("/images");

            File savedFile = new File(uploadDir, fileName);
            savedFile.getParentFile().mkdirs();
            photo.transferTo(savedFile);

            sanPham.setHinhAnh(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            result.rejectValue("hinhAnh", "error.sanPham", "Lỗi khi lưu hình ảnh");
            model.addAttribute("dsLoai", loaiSanPhamRepository.findAll());
            model.addAttribute("dsThuongHieu", thuongHieuRepository.findAll());
            return "admin/sanpham/themsanpham";
        }

        sanPham.setNgayTao(new Date()); // Đặt sau khi qua validate
        sanPhamRepository.save(sanPham);

        return "redirect:/quanly/sanpham/danhsachsanpham";
    }

    // Danh sách sản phẩm
    @GetMapping("/sanpham/danhsachsanpham")
    public String danhSachSanPham(Model model) {
        model.addAttribute("dsSanPham", sanPhamRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "admin/sanpham/danhsachsanpham";
    }

    // Xóa sản phẩm
    @GetMapping("/sanpham/xoa/{id}")
    public String xoaSanPham(@PathVariable("id") Integer id) {
        sanPhamRepository.deleteById(id);
        return "redirect:/quanly/sanpham/danhsachsanpham";
    }

    // Hiển thị form sửa sản phẩm
    @GetMapping("/sanpham/sua/{id}")
    public String hienThiFormSua(@PathVariable("id") Integer id, Model model) {
        sanPham sanPham = sanPhamRepository.findById(id).orElse(null);
        if (sanPham == null) {
            return "redirect:/quanly/sanpham/danhsachsanpham";
        }

        model.addAttribute("sanPham", sanPham);
        model.addAttribute("dsLoai", loaiSanPhamRepository.findAll());
        model.addAttribute("dsThuongHieu", thuongHieuRepository.findAll());
        return "admin/sanpham/suasanpham";
    }

    // Xử lý cập nhật sản phẩm
    @PostMapping("/sanpham/capnhat")
    public String capNhatSanPham(
            @Valid @ModelAttribute("sanPham") sanPham sanPham,
            BindingResult result,
            @RequestParam(name = "hinhAnhFile", required = false) MultipartFile photo,
            Model model
    ) {
        sanPham existing = sanPhamRepository.findById(sanPham.getId()).orElse(null);
        if (existing == null) {
            return "redirect:/quanly/sanpham/danhsachsanpham?error=notfound";
        }

        if (result.hasErrors()) {
            model.addAttribute("dsLoai", loaiSanPhamRepository.findAll());
            model.addAttribute("dsThuongHieu", thuongHieuRepository.findAll());
            return "admin/sanpham/suasanpham";
        }

        existing.setTenSanPham(sanPham.getTenSanPham());
        existing.setMoTa(sanPham.getMoTa());
        existing.setLoaiSanPham(sanPham.getLoaiSanPham());
        existing.setThuongHieu(sanPham.getThuongHieu());

        try {
            if (photo != null && !photo.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                String uploadDir = request.getServletContext().getRealPath("/images");

                File savedFile = new File(uploadDir, fileName);
                savedFile.getParentFile().mkdirs();
                photo.transferTo(savedFile);

                existing.setHinhAnh(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        sanPhamRepository.save(existing);
        return "redirect:/quanly/sanpham/danhsachsanpham";
    }
}
