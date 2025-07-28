package com.asm2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ChiTietSanPham")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class chiTietSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer idChiTietSanPham;

    
    @Column(name = "HinhAnh")
    private String hinhAnh;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải >= 0")
    @Column(name = "SoLuong", nullable = false)
    private Integer soLuong;

    @NotNull(message = "Giá gốc không được để trống")
    @Min(value = 0, message = "Giá gốc phải >= 0")
    @Column(name = "GiaGoc", nullable = false)
    private Integer giaGoc;

    @NotNull(message = "Đơn giá không được để trống")
    @Min(value = 0, message = "Đơn giá phải >= 0")
    @Column(name = "DonGia", nullable = false)
    private Integer donGia;

    @NotNull(message = "Màu sắc không được để trống")
    @ManyToOne
    @JoinColumn(name = "MauSac_Id", nullable = false)
    private mauSac mauSac;

    @NotNull(message = "Kích thước không được để trống")
    @ManyToOne
    @JoinColumn(name = "KichThuoc_Id", nullable = false)
    private kichThuoc kichThuoc;

    @NotNull(message = "Sản phẩm không được để trống")
    @ManyToOne
    @JoinColumn(name = "SanPham_Id", nullable = false)
    private sanPham sanPham;
}
