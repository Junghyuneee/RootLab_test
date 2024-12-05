package com.example.DataList.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.DataList.dto.UserDto;
import com.example.DataList.dto.UserDto2;

@Mapper
public interface UserMapper {
	void inputUsers(UserDto userDto);
	
	List<UserDto> getAllUsers();
	
	List<UserDto2> viewUsers();

}
