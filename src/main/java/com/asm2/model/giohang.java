package com.asm2.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GioHang")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class giohang {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
	  Integer idGioHang;
	
	@Column(name = "SoLuong", nullable = false)
	  int soLuong;
	
	@ManyToOne
    @JoinColumn(name = "ChiTietSanPham_Id", nullable = false)
      chiTietSanPham chiTietSanPham;

    @ManyToOne
    @JoinColumn(name = "TaiKhoan_Id", nullable = false)
      taiKhoan taiKhoan;
}