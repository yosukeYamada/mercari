package com.example.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class LoginUser extends org.springframework.security.core.userdetails.User{
	
	private static final long serialVersionUID = 1L;
	
	/** ユーザー情報 */
	private final User user;
	
	public LoginUser(User user, Collection<GrantedAuthority> authorityList) {
		super(user.getName(), user.getPassword(), authorityList);
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

}

