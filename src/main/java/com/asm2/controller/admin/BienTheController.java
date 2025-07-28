package com.asm2.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.asm2.model.chiTietSanPham;
import com.asm2.model.sanPham;
import com.asm2.model.kichThuoc;
import com.asm2.model.mauSac;
import com.asm2.repository.bienTheRepository;
import com.asm2.repository.sanPhamRepository;

import jakarta.validation.Valid;

import com.asm2.repository.kichThuocRepository;
import com.asm2.repository.mauSacRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/quanly")
public class BienTheController {

    @Autowired
    private bienTheRepository bienTheRepository;

    @Autowired
    private sanPhamRepository sanPhamRepository;

    @Autowired
    private kichThuocRepository kichThuocRepository;

    @Autowired
    private mauSacRepository mauSacRepository;
    @GetMapping("/sanpham/danhsachbienthe")
    public String danhSachBienThe(Model model) {
        List<chiTietSanPham> dsBienThe = bienTheRepository.findAll(Sort.by(Sort.Direction.DESC, "idChiTietSanPham"));
        model.addAttribute("dsBienThe", dsBienThe);
        return "/admin/sanpham/danhsachbienthe";
    }

    @GetMapping("/sanpham/thembienthe")
    public String thembienthe(Model model) {
        model.addAttribute("bienThe", new chiTietSanPham());
        model.addAttribute("dsSanPham", sanPhamRepository.findAll());
        model.addAttribute("dsKichThuoc", kichThuocRepository.findAll());
        model.addAttribute("dsMauSac", mauSacRepository.findAll());
        return "/admin/sanpham/thembienthe";
    }

    @PostMapping("/sanpham/luubienthe")
    public String luuBienThe(
            @Valid @ModelAttribute("bienThe") chiTietSanPham bienThe,
            BindingResult result,
            @RequestParam("hinhAnhFile") MultipartFile hinhAnhFile,
            Model model
    ) {
        // Xử lý lỗi thủ công nếu thiếu ảnh
        if ((hinhAnhFile == null || hinhAnhFile.isEmpty()) && (bienThe.getHinhAnh() == null || bienThe.getHinhAnh().isBlank())) {
            result.rejectValue("hinhAnh", "error.hinhAnh", "Hình ảnh không được để trống");
        }

        if (result.hasErrors()) {
            model.addAttribute("dsSanPham", sanPhamRepository.findAll());
            model.addAttribute("dsKichThuoc", kichThuocRepository.findAll());
            model.addAttribute("dsMauSac", mauSacRepository.findAll());
            return "/admin/sanpham/thembienthe";
        }

        try {
            if (hinhAnhFile != null && !hinhAnhFile.isEmpty()) {
                String tenFile = hinhAnhFile.getOriginalFilename();
                String duongDan = new File("src/main/resources/static/images").getAbsolutePath();
                File destination = new File(duongDan + File.separator + tenFile);
                hinhAnhFile.transferTo(destination);
                bienThe.setHinhAnh(tenFile);
            }

            bienTheRepository.save(bienThe);
            return "redirect:/quanly/sanpham/danhsachbienthe";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/quanly/sanpham/thembienthe?error";
        }
    }




    @GetMapping("/sanpham/suabienthe/{id}")
    public String suaBienThe(@PathVariable("id") Integer id, Model model) {
        Optional<chiTietSanPham> bienThe = bienTheRepository.findById(id);
        if (bienThe.isPresent()) {
            model.addAttribute("bienThe", bienThe.get());
            model.addAttribute("dsSanPham", sanPhamRepository.findAll());
            model.addAttribute("dsKichThuoc", kichThuocRepository.findAll());
            model.addAttribute("dsMauSac", mauSacRepository.findAll());
            return "/admin/sanpham/suabienthe";
        } else {
            return "redirect:/quanly/sanpham/danhsachbienthe";
        }
    }

    @PostMapping("/sanpham/capnhatbienthe")
    public String capNhatBienThe(
        @ModelAttribute("bienThe") chiTietSanPham bienThe,
        @RequestPart(name = "hinhAnhFile", required = false) MultipartFile file
    ) {
        chiTietSanPham existing = bienTheRepository.findById(bienThe.getIdChiTietSanPham()).orElse(null);
        if (existing == null) {
            // Xử lý nếu không tìm thấy bản ghi cũ
            return "redirect:/quanly/sanpham/danhsach";
        }

        // Nếu không chọn hình mới, giữ lại hình cũ
        if (file == null || file.isEmpty()) {
            bienThe.setHinhAnh(existing.getHinhAnh());
        } else {
            // Nếu có chọn hình mới thì xử lý lưu ảnh mới và gán tên file
            String fileName = file.getOriginalFilename();
            Path uploadPath = Paths.get("uploads"); // Tùy đường dẫn thực tế
            try {
                Files.createDirectories(uploadPath);
                file.transferTo(uploadPath.resolve(fileName));
                bienThe.setHinhAnh(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bienTheRepository.save(bienThe);
        return "redirect:/quanly/sanpham/danhsachbienthe";
    }


    @GetMapping("/sanpham/xoabienthe/{id}")
    public String xoaBienThe(@PathVariable("id") Integer id) {
        bienTheRepository.deleteById(id);
        return "redirect:/quanly/sanpham/danhsachbienthe";
    }
}
