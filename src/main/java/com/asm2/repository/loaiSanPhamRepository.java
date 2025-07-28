package com.asm2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.asm2.model.loaiSanPham;

public interface loaiSanPhamRepository extends JpaRepository<loaiSanPham, Integer> {
    
	 List<loaiSanPham> findByTenLoaiSanPhamContainingIgnoreCase(String keyword);
	    loaiSanPham findByTenLoaiSanPham(String tenLoaiSanPham);
	    boolean existsByTenLoaiSanPhamIgnoreCase(String tenLoai);

    

}
