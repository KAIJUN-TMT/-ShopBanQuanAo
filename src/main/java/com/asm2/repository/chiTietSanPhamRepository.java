package com.asm2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asm2.model.chiTietSanPham;


public interface chiTietSanPhamRepository extends JpaRepository<chiTietSanPham, Integer> {
    
	List<chiTietSanPham> findBySanPham_Id(Integer id);

	
}