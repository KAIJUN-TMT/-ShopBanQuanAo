package com.asm2.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "KichThuoc")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class kichThuoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Integer id;

    @Column(name = "TenKichThuoc", nullable = false, columnDefinition = "nvarchar(3)")
      String tenKichThuoc;

    @Column(name = "TrangThai", nullable = false)
      Boolean trangThai;
   
}
