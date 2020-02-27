package com.example.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.repository.UserRepository;

//@DependsOn({"SecurityConfig.PasswordEncoder"})

@Service
@Transactional
public class UserService{
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailSender mailSender;
	
	public boolean registerUser(RegisterUserForm form) {
		String name = form.getName();
		passwordEncoder.hashCode();
		if(userRepository.load(name) == null){
			return true;
		}else {
			return false;
		}
	}
	

	public void registerUserFlush(RegisterUserForm form) {
		User user = new User();
//		user.setName(form.getEmail());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
//		String ip = myIP.getYourIp();
		String vali = UUID.randomUUID().toString();
		user.setUuid(vali);
		user.setRegisterDate(timestamp);
		user.setAuthority(0);
		userRepository.insertFlush(user);
		
		String ipAndPort = "localhoust:8080";
		String from = "yamada_141@yahoo.co.jp";
		String title = "アカウント確認のお願い";
		String content = "ゲストさん"+"\n"+"\n"+"以下のリンクにアクセスしてアカウント認証をしてください"+"\n"+"http://"+ipAndPort+"/validate"+"?id="+vali;
		String status = "エラー：メール送信失敗";
		
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom(from);
			msg.setTo(form.getName());
			msg.setSubject(title);
			msg.setText(content);
			mailSender.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
//			return status;
			// TODO: handle exception
		}
		status="ok";
	}
//		return status;
		

}
