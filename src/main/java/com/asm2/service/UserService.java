package com.asm2.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.asm2.model.taiKhoan;
import com.asm2.repository.taiKhoanRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private taiKhoanRepository taiKhoanRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        taiKhoan taikhoan = taiKhoanRepository.findByEmail(email);

        if (taikhoan != null) {
            Set<GrantedAuthority> authorities = new HashSet<>();

            
            String role = taikhoan.getVaiTro() ? "ROLE_ADMIN" : "ROLE_USER";

            authorities.add(new SimpleGrantedAuthority(role));

            return new User(
                taikhoan.getEmail(),           
                taikhoan.getMatKhau(),         
                authorities                    
            );
        }
        

        throw new UsernameNotFoundException("Email không tồn tại: " + email);
    }
}
