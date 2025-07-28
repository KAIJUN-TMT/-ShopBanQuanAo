package com.asm2.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class BatLoiBoTrongDangNhap implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("eremail", "Vui lòng nhập email.");
            request.getRequestDispatcher("/dangnhap").forward(request, response);
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("erpwnull", "Vui lòng nhập mật khẩu.");
            request.getRequestDispatcher("/dangnhap").forward(request, response);
            return;
        }

        // Đăng nhập sai
        request.setAttribute("ertk", "Email hoặc mật khẩu không đúng.");
        response.sendRedirect("/dangnhap");
    }
}
