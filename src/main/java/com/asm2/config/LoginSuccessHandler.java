package com.asm2.config;

import com.asm2.model.taiKhoan;
import com.asm2.repository.taiKhoanRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private taiKhoanRepository taiKhoanRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName(); 
        taiKhoan taikhoan = taiKhoanRepository.findByEmail(email);
        if (taikhoan != null) {
            HttpSession session = request.getSession();
            session.setAttribute("taikhoan", taikhoan); 
        }

        
        response.sendRedirect("/trangchu");
    }
}
