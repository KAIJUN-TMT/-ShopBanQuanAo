package com.asm2.controller.admin;

import com.asm2.model.kichThuoc;
import com.asm2.repository.kichThuocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quanly")
public class KichThuocController {

    @Autowired
    private kichThuocRepository Repo;

    // Hiển thị danh sách kích thước + form
    @GetMapping("/sanpham/kichthuoc")
    public String hienThiDanhSach(Model model) {
        model.addAttribute("dsKichThuoc", Repo.findAll());
        model.addAttribute("kichThuoc", new kichThuoc());
        return "admin/sanpham/kichthuoc";
    }

    // Lưu (thêm hoặc cập nhật)
    @PostMapping("/sanpham/kichthuoc/luu")
    public String luuKichThuoc(@ModelAttribute kichThuoc kichThuocInput, Model model) {

        // Kiểm tra tên bỏ trống
        if (kichThuocInput.getTenKichThuoc() == null || kichThuocInput.getTenKichThuoc().trim().isEmpty()) {
            model.addAttribute("dsKichThuoc", Repo.findAll());
            model.addAttribute("tenbotrong", "Tên kích thước không được bỏ trống");
            model.addAttribute("kichThuoc", kichThuocInput); // giữ lại dữ liệu người dùng đã nhập
            return "admin/sanpham/kichthuoc";
        }

        // Kiểm tra trùng tên
        boolean isDuplicate = false;

        kichThuoc existing = Repo.findByTenKichThuoc(kichThuocInput.getTenKichThuoc());
        if (existing != null) {
            Integer inputId = kichThuocInput.getId();
            Integer existingId = existing.getId();

            if (inputId == null || !inputId.equals(existingId)) {
                isDuplicate = true;
            }
        }

        if (isDuplicate) {
            model.addAttribute("dsKichThuoc", Repo.findAll());
            model.addAttribute("error", "Tên kích thước đã tồn tại");
            model.addAttribute("kichThuoc", kichThuocInput);
            return "admin/sanpham/kichthuoc";
        }

        // Nếu trạng thái null => mặc định true
        if (kichThuocInput.getTrangThai() == null) {
            kichThuocInput.setTrangThai(true);
        }

        // Lưu vào DB
        Repo.save(kichThuocInput);

        return "redirect:/quanly/sanpham/kichthuoc";
    }

    // Sửa: đổ dữ liệu lên form
    @GetMapping("/sanpham/kichthuoc/sua/{id}")
    public String suaKichThuoc(@PathVariable("id") Integer id, Model model) {
        kichThuoc kt = Repo.findById(id).orElse(new kichThuoc());
        model.addAttribute("kichThuoc", kt);
        model.addAttribute("dsKichThuoc", Repo.findAll());
        return "admin/sanpham/kichthuoc";
    }

    // Xóa
    @GetMapping("/sanpham/kichthuoc/xoa/{id}")
    public String xoaKichThuoc(@PathVariable("id") Integer id) {
        Repo.deleteById(id);
        return "redirect:/quanly/sanpham/kichthuoc";
    }

    // Tìm kiếm
    @PostMapping("/sanpham/kichthuoc/tim")
    public String timKichThuoc(@RequestParam("keyword") String keyword, Model model) {
        List<kichThuoc> dsKichThuoc = Repo.findByTenKichThuocContainingIgnoreCase(keyword);
        model.addAttribute("dsKichThuoc", dsKichThuoc);
        model.addAttribute("kichThuoc", new kichThuoc());
        return "admin/sanpham/kichthuoc";
    }
}
