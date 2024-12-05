package com.example.DataList.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DataList.dto.UserDto;
import com.example.DataList.dto.UserDto2;
import com.example.DataList.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public void inputUsers(UserDto userDto) {
		userMapper.inputUsers(userDto);
	}
	
	public List<UserDto> getAllUsers(){
		return userMapper.getAllUsers();
	}
	
	public List<UserDto2> viewUsers(){
		return userMapper.viewUsers();
	}
}
