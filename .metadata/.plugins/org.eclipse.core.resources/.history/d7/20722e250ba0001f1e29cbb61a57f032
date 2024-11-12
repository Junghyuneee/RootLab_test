package com.example.AddressBook.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AddressBook.model.User;

@RestController
@RequestMapping("/api/addressbook")
public class AddressBookController {
	private Map<String, User> addressBook = new HashMap<>();
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/list")
	public Collection<User> getUsers(){
		return addressBook.values();
	}
	
	@PostMapping("/save")
	public String saveUser(@RequestBody User user) {
		if(addressBook.containsKey(user.getName())) {
			return "중복된 이름이 있습니다.";
		}
		addressBook.put(user.getName(), user);
		return "저장이 완료되었습니다.";
	}

}
