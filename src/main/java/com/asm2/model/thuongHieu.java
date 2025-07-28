package com.asm2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ThuongHieu")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class thuongHieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
     Integer id;

    @Column(name = "TenThuongHieu", nullable = false,columnDefinition = "nvarchar(250)")
     String tenThuongHieu;

    @Column(name = "TrangThai", nullable = false)
     Boolean trangThai;

    
    @OneToMany(mappedBy = "thuongHieu")
    private List<sanPham> sanPhams;
}
