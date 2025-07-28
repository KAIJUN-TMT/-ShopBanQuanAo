package com.asm2.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TaiKhoan")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class taiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Integer id;

    @Column(name = "HoTen", nullable = false, length = 125 ,columnDefinition = "nvarchar(60)")
    String hoTen;

    @Column(name = "Email", nullable = false, length = 250, unique = true)
     String email;

    @Column(name = "MatKhau", nullable = false, length = 250)
     String matKhau;

    @Temporal(TemporalType.DATE)
    @Column(name = "NgaySinh", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
     Date ngaySinh;

    @Column(name = "GioiTinh", nullable = false)
     Boolean gioiTinh;

    @Column(name = "SoDienThoai", nullable = false, length = 10)
     String soDienThoai;

    @Temporal(TemporalType.DATE)
    @Column(name = "NgayDangKy", nullable = false)
     Date ngayDangKy;

    @Column(name = "TrangThai", nullable = false)
     Boolean trangThai;

    @Column(name = "VaiTro", nullable = false)
     Boolean vaiTro;

    @Column(name = "reset_token")
     String resetToken;
    

    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }
    
    @OneToMany(mappedBy = "taiKhoan",fetch = FetchType.LAZY)
    private List<donHang> donHangs;
    
    @Override
    public String toString() {
        return "taiKhoan{" +"id=" + id +", email='" + email + '\'' +", matKhau='" + matKhau + '\'' +'}';
}}