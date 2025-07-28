package com.asm2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asm2.repository.donHangRepository;
import com.asm2.repository.taiKhoanRepository;
import com.asm2.repository.chiTietDonHangRepository;
import com.asm2.model.taiKhoan;

import java.util.*;

@Service
public class ThongKeService {
	@Autowired
	private donHangRepository donHangRepo;

	@Autowired
	private taiKhoanRepository taiKhoanRepo;

	/** Lấy TOP «top» khách mua nhiều nhất */
	public List<Map<String, Object>> layKhachHangMuaNhieuNhat(int top) {

		List<Object[]> raw = donHangRepo.thongKeLuotMua();
		List<Map<String, Object>> kq = new ArrayList<>();

		for (int i = 0; i < Math.min(top, raw.size()); i++) {

			Object[] row = raw.get(i);

			Integer taiKhoanId = (Integer) row[0];
			Long soDonHang = (Long) row[1]; // COUNT(*)
			Long tongChiTieu = (Long) row[2]; // SUM(tongTien)

			taiKhoan tk = taiKhoanRepo.findById(taiKhoanId).orElse(null);
			if (tk == null)
				continue;

			Map<String, Object> data = new HashMap<>();
			data.put("taiKhoanId", taiKhoanId);
			data.put("hoTen", tk.getHoTen());
			data.put("soLuongMua", soDonHang); // để nguyên Long cho chính xác
			data.put("tongChiTieu", tongChiTieu); // Long

			kq.add(data);
		}
		return kq;
	}
	
	
	/** Lấy TOP «top» sản phẩm bán chạy nhất */
	@Autowired
    private chiTietDonHangRepository chiTietDonHangRepository;

    public List<Map<String, Object>> layTopSanPhamBanChay(int top) {
        List<Object[]> rawData = chiTietDonHangRepository.thongKeSanPhamBanChay();
        List<Map<String, Object>> ketQua = new ArrayList<>();

        for (int i = 0; i < Math.min(top, rawData.size()); i++) {
            Object[] row = rawData.get(i);

            Map<String, Object> data = new HashMap<>();
            data.put("tenSanPham", row[0]);
            data.put("tongSoLuong", ((Number) row[1]).intValue());
            data.put("tongDoanhThu", ((Number) row[2]).longValue());
            ketQua.add(data);
        }

        return ketQua;
    }
    
    /** Thống kê số lượng đơn hàng theo ngày */
    public List<Map<String, Object>> layThongKeDonHangTheoNgay() {
        List<Object[]> rawData = donHangRepo.thongKeDonHangTheoNgay();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : rawData) {
            Map<String, Object> item = new HashMap<>();
            item.put("ngay", row[0]); // java.util.Date
            item.put("soLuongDon", ((Number) row[1]).intValue());
            item.put("doanhThu", ((Number) row[2]).longValue());
            result.add(item);
        }

        return result;
    }

}
