package com.asm2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ChiTietDonHang")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class chiTietDonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Integer idChiTietDonHang;

    @Column(name = "SoLuong", nullable = false)
    int soLuong;

    @Column(name = "GiaTien", nullable = false)
     int giaTien;

    @ManyToOne
    @JoinColumn(name = "ChiTietSanPham_Id", nullable = false)
     chiTietSanPham chiTietSanPham;

    @ManyToOne
    @JoinColumn(name = "DonHang_Id", nullable = false)
     donHang donHang;
}
