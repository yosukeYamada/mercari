package com.example.form;

/**
 * ユーザー登録フォーム
 * 
 * @author yosuke.yamada
 *
 */
public class RegisterUserForm {

	private String name;
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "RegisterUserForm [name=" + name + ", password=" + password + "]";
	}

}
