package com.asm2.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asm2.model.taiKhoan;
import com.asm2.repository.taiKhoanRepository;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
	@Autowired
	taiKhoanRepository taikhoanRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@RequestMapping("/dangky")
	public String hienthidangKy( Model model) {
		model.addAttribute("taikhoan", new taiKhoan());
		return ("/dangky");
	}

	@PostMapping("/dangky")
	public String xulydangky(
	        @ModelAttribute("taikhoan") taiKhoan taikhoan,
	        @RequestParam("repeatPassword") String repeatPassword,
	        Model model
	) {
	    boolean hasError = false;

	    String fullName = taikhoan.getHoTen();
	    String email = taikhoan.getEmail();
	    String phone = taikhoan.getSoDienThoai();
	    String password = taikhoan.getMatKhau();
	    Date birthday = taikhoan.getNgaySinh();
	    Boolean gender = taikhoan.getGioiTinh();

	    // Kiểm tra bỏ trống
	    if (fullName == null || fullName.trim().isEmpty()) {
	        model.addAttribute("errorFullName", "Họ tên không được bỏ trống");
	        hasError = true;
	    }
	    if (email == null || email.trim().isEmpty()) {
	        model.addAttribute("errorEmail", "Email không được bỏ trống");
	        hasError = true;
	    }
	    if (phone == null || phone.trim().isEmpty()) {
	        model.addAttribute("errorPhone", "Số điện thoại không được bỏ trống");
	        hasError = true;
	    }
	    if (password == null || password.trim().isEmpty()) {
	        model.addAttribute("errorPassword", "Mật khẩu không được bỏ trống");
	        hasError = true;
	    }
	    if (repeatPassword == null || repeatPassword.trim().isEmpty()) {
	        model.addAttribute("errorRepeatPassword", "Vui lòng nhập lại mật khẩu");
	        hasError = true;
	    }

	    // Kiểm tra mật khẩu khớp
	    if (password != null && !password.equals(repeatPassword)) {
	        model.addAttribute("errorRepeatPassword", "Mật khẩu nhập lại không khớp");
	        hasError = true;
	    }

	    // Kiểm tra email đã tồn tại
	    if (email != null && taikhoanRepository.findByEmail(email) != null) {
	        model.addAttribute("errorEmail", "Email đã được đăng ký");
	        hasError = true;
	    }

	    // Nếu có lỗi thì giữ lại dữ liệu người dùng đã nhập
	    if (hasError) {
	        taiKhoan input = new taiKhoan();
	        input.setHoTen(fullName);
	        input.setEmail(email);
	        input.setSoDienThoai(phone);
	        input.setNgaySinh(birthday);
	        input.setGioiTinh(gender);
	        input.setMatKhau(password); // giữ lại mật khẩu nếu cần
	        model.addAttribute("taikhoan", input);
	        return "/dangky";
	    }

	    try {
	        taikhoan.setHoTen(fullName);
	        taikhoan.setEmail(email);
	        taikhoan.setSoDienThoai(phone);
	        taikhoan.setNgaySinh(birthday);
	        taikhoan.setGioiTinh(gender);
	        taikhoan.setNgayDangKy(new Date());
	        taikhoan.setTrangThai(true);
	        taikhoan.setVaiTro(false); 

	        // Mã hóa mật khẩu trước khi lưu
	        taikhoan.setMatKhau(passwordEncoder.encode(password));

	        taikhoanRepository.save(taikhoan);

	        return "redirect:/dangnhap";

	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("errorSystem", "Lỗi hệ thống, vui lòng thử lại sau.");
	        return "/dangky";
	    }
	}

	
	@RequestMapping("/dangnhap")
	public String dangnhap(HttpServletRequest request, Model model) {
	    
		return ("/dangnhap");
	}
	
//	@RequestMapping("/dangnhaploi")
//	public String dangnhaploi(HttpServletRequest request, Model model) {
//		
//		Object emailEmpty = request.getAttribute("eremail");
//	    Object passwordEmpty = request.getAttribute("erpwnull");
//	    Object ertk = request.getAttribute("ertk");
//
//	    model.addAttribute("eremail", emailEmpty);
//	    model.addAttribute("erpwnull", passwordEmpty);
//	    model.addAttribute("ertk", ertk);
//
//	    
//		return ("/dangnhap");
//	}
	
	
	
//	@PostMapping("/dangnhap")
//	public String login(@RequestParam("email") String email,
//	                    @RequestParam("password") String password,
//	                    Model model,
//	                    HttpServletRequest request) {
//
//	    boolean hasError = false;
//
//	    if (email == null || email.trim().isEmpty()) {
//	        model.addAttribute("eremail", "Email không được bỏ trống");
//	        hasError = true;
//	    }
//
//	    if (password == null || password.trim().isEmpty()) {
//	        model.addAttribute("erpwnull", "Mật khẩu không được bỏ trống");
//	        hasError = true;
//	    }
//
//	    if (hasError) {
//	    	taiKhoan loginInput = new taiKhoan();
//	    	loginInput.setEmail(email);
//	    	loginInput.setMatKhau(password);
//	    	model.addAttribute("loginInput", loginInput);
//
//	        return "dangnhap";
//	    }
//
//	    taiKhoan taikhoan = taikhoanRepository.findByEmail(email);
//	    if (taikhoan == null) {
//	        model.addAttribute("ertk", "Email chưa được đăng ký");
//	        taiKhoan loginInput = new taiKhoan();
//	        loginInput.setEmail(email);
//	        loginInput.setMatKhau(password);
//	        model.addAttribute("loginInput", loginInput);
//
//	        return "dangnhap";
//	    }
//
//	    if (!passwordEncoder.matches(password, taikhoan.getMatKhau())) {
//	        model.addAttribute("erpw", "Mật khẩu không đúng");
//	        taiKhoan loginInput = new taiKhoan();
//	        loginInput.setEmail(email);
//	        loginInput.setMatKhau(password);
//	        model.addAttribute("loginInput", loginInput);
//
//	        return "dangnhap";
//	    }
//
//	    
//	    HttpSession session = request.getSession();
//	    session.setAttribute("taikhoan", taikhoan);
//	    return "redirect:/trangchu";
//	}

	
	
	@RequestMapping("/quenmatkhau")
	public String quenmatkhau() {
		return ("/quenmatkhau");
	}
	
	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	HttpServletRequest request; 

	@PostMapping("/quenmatkhau")
	public String xuLyQuenMatKhau(
	        @RequestParam("email") String email,
	        Model model,
	        HttpServletRequest request) {

	    // Kiểm tra email bỏ trống
	    if (email == null || email.trim().isEmpty()) {
	        model.addAttribute("error", "Vui lòng nhập email!");
	        return "quenmatkhau";
	    }

	    taiKhoan taikhoan = taikhoanRepository.findByEmail(email.trim());

	    // Kiểm tra email không tồn tại
	    if (taikhoan == null) {
	        model.addAttribute("error", "Email không tồn tại trong hệ thống!");
	        return "quenmatkhau";
	    }

	    // Tạo token và lưu vào database
	    String token = java.util.UUID.randomUUID().toString();
	    taikhoan.setResetToken(token);
	    taikhoanRepository.save(taikhoan);

	    // Tạo link đặt lại mật khẩu
	    String resetLink = request.getRequestURL().toString().replace("/quenmatkhau", "") + "/datlaimatkhau?token=" + token;

	    // Gửi email
	    try {
	        MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	        helper.setTo(email);
	        helper.setSubject("Đặt lại mật khẩu");
	        helper.setText("Nhấn vào liên kết sau để đặt lại mật khẩu:\n\n" + resetLink);

	        javaMailSender.send(message);
	        model.addAttribute("message", "Email đặt lại mật khẩu đã được gửi!");
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("error", "Gửi email thất bại. Vui lòng thử lại sau!");
	    }

	    return "quenmatkhau";
	}

	
	@GetMapping("/datlaimatkhau")
	public String formDatLai(@RequestParam("token") String token, Model model) {
	    taiKhoan user = taikhoanRepository.findByResetToken(token);
	    
	    if (token == null || token.isEmpty()) {
	        model.addAttribute("message", "Thiếu mã xác thực (token)");
	        System.out.println("Tokennnnnnnnnnn nè: "+ token);
	        return "datlaimatkhau";
	    }
	    if (user == null) {
	        model.addAttribute("message", "Token không hợp lệ");
	        return "datlaimatkhau";
	    }
	    model.addAttribute("token", token);
	    return "datlaimatkhau";
	}

	@PostMapping("/datlaimatkhau")
	public String datLaiMatKhau(
	        @RequestParam("token") String token,
	        @RequestParam("newPassword") String newPassword,
	        @RequestParam("repeatPassword") String repeatPassword,
	        Model model) {

	    if (token == null || token.trim().isEmpty()) {
	        model.addAttribute("message", "Mã xác thực không hợp lệ");
	        return "datlaimatkhau";
	    }

	    taiKhoan user = taikhoanRepository.findByResetToken(token);
	    if (user == null) {
	        model.addAttribute("message", "Token không tồn tại hoặc đã hết hạn");
	        return "datlaimatkhau";
	    }

	    if (newPassword.trim().isEmpty() ) {
	        model.addAttribute("errorNewPassword", "Vui lòng nhập mật khẩu mới");
	        model.addAttribute("token", token);
	        return "datlaimatkhau";
	    }
	    if ( newPassword.trim().isEmpty()) {
	        model.addAttribute("errorRepeatPassword", "Vui lòng xác nhận mật khẩu mới");
	        model.addAttribute("token", token);
	        return "datlaimatkhau";
	    }
	    
	    if (!newPassword.equals(repeatPassword)) {
	        model.addAttribute("errorRepeatPassword", "Mật khẩu nhập lại không khớp");
	        model.addAttribute("token", token);
	        return "datlaimatkhau";
	    }

	    user.setMatKhau(passwordEncoder.encode(newPassword));
	    user.setResetToken(null);
	    taikhoanRepository.save(user);

	    model.addAttribute("message", "Đặt lại mật khẩu thành công!");
	    return "redirect:/dangnhap";
	}

	
	
	
	
	@RequestMapping("/doimatkhau")
	public String doimatkhau() {
		return ("/doimatkhau");
	}
	@PostMapping("/doimatkhau")
	public String doiMatKhau(
	        HttpServletRequest request,
	        @RequestParam("password") String oldPassword,
	        @RequestParam("passwordNew") String newPassword,
	        @RequestParam("repeatPasswordNew") String repeatPassword,
	        Model model,
	        Authentication authentication) {

	    String email = authentication.getName();
	    taiKhoan taikhoan = taikhoanRepository.findByEmail(email);

	    if (taikhoan == null) {
	        model.addAttribute("error", "Không tìm thấy tài khoản!");
	        return "/doimatkhau";
	    }

	    // Kiểm tra mật khẩu cũ đúng không
	    if (oldPassword == null || oldPassword.trim().isEmpty()) {
	        model.addAttribute("errorOldPassword", "Vui lòng nhập mật khẩu hiện tại");
	        return "/doimatkhau";
	    }

	    if (!passwordEncoder.matches(oldPassword, taikhoan.getMatKhau())) {
	        model.addAttribute("errorOldPassword", "Mật khẩu hiện tại không đúng");
	        return "/doimatkhau";
	    }

	    // Kiểm tra mật khẩu mới
	    if (newPassword == null || newPassword.trim().isEmpty()) {
	        model.addAttribute("errorNewPassword", "Vui lòng nhập mật khẩu mới");
	        return "/doimatkhau";
	    }

	    if (!newPassword.equals(repeatPassword)) {
	        model.addAttribute("errorRepeatPassword", "Mật khẩu mới nhập lại không khớp");
	        return "/doimatkhau";
	    }

	    // Lưu mật khẩu mới
	    taikhoan.setMatKhau(passwordEncoder.encode(newPassword));
	    taikhoanRepository.save(taikhoan);

	    model.addAttribute("success", "Đổi mật khẩu thành công!");
	    return "/doimatkhau";
	}

}
