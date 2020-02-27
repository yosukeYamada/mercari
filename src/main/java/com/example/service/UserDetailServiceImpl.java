package com.example.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.repository.UserRepository;

public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException{
		User user = userRepository.load(name);
		if (user == null) {
			throw new UsernameNotFoundException("そのメールアドレスは登録されていません");
		}
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new LoginUser(user, authorityList);

	}

}
