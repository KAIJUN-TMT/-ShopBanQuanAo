package com.asm2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LoaiSanPham")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class loaiSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
      Integer id;

    @Column(name = "TenLoaiSanPham", nullable = false, columnDefinition = "nvarchar(250)")
      String tenLoaiSanPham;

    @Column(name = "TrangThai", nullable = false)
      Boolean trangThai;
}
