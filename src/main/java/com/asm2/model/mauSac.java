package com.asm2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MauSac")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class mauSac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
      Integer id;

    @Column(name = "TenMauSac", nullable = false,columnDefinition = "nvarchar(250)")
      String tenMauSac;

    @Column(name = "TrangThai", nullable = false)
      Boolean trangThai;

}
