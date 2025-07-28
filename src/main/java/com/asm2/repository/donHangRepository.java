package com.asm2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asm2.model.donHang;

@Repository
public interface donHangRepository extends JpaRepository<donHang, Integer> {
	@Query("""
			SELECT d.taiKhoan.id      AS id,
			       COUNT(d)           AS cnt,
			       SUM(d.tongTien)    AS total
			FROM   donHang d
			GROUP  BY d.taiKhoan.id
			ORDER  BY COUNT(d) DESC
			""")
	List<Object[]> thongKeLuotMua();
	
	@Query("""
		    SELECT d.ngayDat,
		           COUNT(d),
		           SUM(d.tongTien)
		    FROM donHang d
		    GROUP BY d.ngayDat
		    ORDER BY d.ngayDat DESC
		""")
		List<Object[]> thongKeDonHangTheoNgay();

}
