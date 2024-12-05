package com.example.DataList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.DataList.dto.UserDto;
import com.example.DataList.dto.UserDto2;
import com.example.DataList.service.UserService;


@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String getAllUsers(Model model) {
		List<UserDto> users = userService.getAllUsers();
		List<UserDto2> viewUsers = userService.viewUsers();
		model.addAttribute("viewUsers", viewUsers);
		model.addAttribute("users", users);
		model.addAttribute("inputUsers", new UserDto());
		return "userList";
		
	}
	
	@PostMapping
	public String inputUsers(UserDto userDto, Model model) {
		userService.inputUsers(userDto);
		List<UserDto> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "userList";
	}
}
