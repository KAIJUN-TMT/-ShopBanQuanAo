package com.asm2.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.asm2.model.taiKhoan;


public interface taiKhoanRepository extends JpaRepository<taiKhoan, Integer>{
	public taiKhoan findByEmail(String email);
	public taiKhoan findByResetToken(String token);
}
