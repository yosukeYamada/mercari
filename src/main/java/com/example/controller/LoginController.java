package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.RegisterUserForm;
import com.example.service.UserService;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private ItemListController itemListController;

	@Autowired
	private UserService userService;

	@RequestMapping("")
	public String index() {
		return "login";
	}

	@RequestMapping("/registerUser")
	public String showRegisterUserForm() {
		return "register";
	}

	@RequestMapping("/registerAccount")
	public String registerAccount(@Validated RegisterUserForm form, BindingResult result, Model model) {
		boolean checkUser = userService.registerUser(form);
		if (checkUser == true) {
			userService.registerUserFlush(form);
		} else {
			result.rejectValue(null, null, "そのメールアドレスは既に登録されています。");
			return "/registerUser";
		}

		return "/";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		return "forward:/itemList";
	}

}
