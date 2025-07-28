package com.asm2.repository;

import com.asm2.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface thuongHieuRepository extends JpaRepository<thuongHieu, Integer> {
	List<thuongHieu> findByTenThuongHieuContainingIgnoreCase(String keyword);
    thuongHieu findByTenThuongHieu(String tenThuongHieu);
    boolean existsByTenThuongHieuIgnoreCase(String tenThuongHieu);

   

}
