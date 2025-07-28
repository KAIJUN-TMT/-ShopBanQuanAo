package com.asm2.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SanPham")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class sanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Column(name = "TenSanPham", nullable = false, columnDefinition = "nvarchar(250)")
    private String tenSanPham;
    
    
    @Column(name = "HinhAnh")
    private String hinhAnh;

    @NotBlank(message = "Mô tả không được để trống")
    @Column(name = "MoTa", nullable = false, columnDefinition = "nvarchar(250)")
    private String moTa;

    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "NgayTao", nullable = false)
    private Date ngayTao;

    @NotNull(message = "Loại sản phẩm không được để trống")
    @ManyToOne
    @JoinColumn(name = "LoaiSanPham_Id", nullable = false)
    private loaiSanPham loaiSanPham;

    @NotNull(message = "Thương hiệu không được để trống")
    @ManyToOne
    @JoinColumn(name = "ThuongHieu_Id", nullable = false)
    private thuongHieu thuongHieu;

    @OneToMany(mappedBy = "sanPham", fetch = FetchType.LAZY)
    private List<chiTietSanPham> chiTietSanPham;
}
