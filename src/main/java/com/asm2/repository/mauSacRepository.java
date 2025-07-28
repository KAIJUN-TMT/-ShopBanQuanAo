package com.asm2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asm2.model.mauSac;

public interface mauSacRepository extends JpaRepository<mauSac, Integer> {
	List<mauSac> findByTenMauSacContainingIgnoreCase(String keyword);
	mauSac findByTenMauSac(String tenMau);
    boolean existsByTenMauSacIgnoreCase(String tenMau);

}
