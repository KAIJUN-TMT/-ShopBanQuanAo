package com.asm2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.asm2.model.chiTietSanPham;

@Repository
public interface bienTheRepository extends JpaRepository<chiTietSanPham, Integer> {
    
}