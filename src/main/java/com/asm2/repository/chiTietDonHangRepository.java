package com.asm2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.asm2.model.chiTietDonHang;

@Repository
public interface chiTietDonHangRepository extends JpaRepository<chiTietDonHang, Integer> {
	@Query("""
	        SELECT sp.tenSanPham,
	               SUM(ct.soLuong),
	               SUM(ct.soLuong * ct.giaTien)
	        FROM chiTietDonHang ct
	        JOIN ct.chiTietSanPham ctp
	        JOIN ctp.sanPham sp
	        GROUP BY sp.tenSanPham
	        ORDER BY SUM(ct.soLuong) DESC
	        """)
	    List<Object[]> thongKeSanPhamBanChay();
}
