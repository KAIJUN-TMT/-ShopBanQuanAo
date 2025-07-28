package com.asm2.repository;

import com.asm2.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface kichThuocRepository extends JpaRepository<kichThuoc, Integer> {
	List<kichThuoc> findByTenKichThuocContainingIgnoreCase(String keyword);
	 kichThuoc findByTenKichThuoc(String tenKichThuoc);
	    boolean existsByTenKichThuocIgnoreCase(String tenKichThuoc);
}