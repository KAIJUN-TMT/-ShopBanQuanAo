package com.asm2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "DonHang")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class donHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
      Integer idDonHang;

    @Temporal(TemporalType.DATE)
    @Column(name = "NgayDat", nullable = false)
      Date ngayDat;

    @Column(name = "TongTien", nullable = false)
      int tongTien;

    @Column(name = "TrangThai", nullable = false)
      int trangThai;

    @ManyToOne
    @JoinColumn(name = "TaiKhoan_Id", nullable = false)
      taiKhoan taiKhoan;
}
