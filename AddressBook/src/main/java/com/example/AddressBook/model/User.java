package com.example.AddressBook.model;

import lombok.Data;

@Data
public class User {
	private String name;
	private int age;
	private String phone;
	private String address;
	
	// 생성자 getter, setter
	/*
	 * public User(String name, int age, String phone, String address) { this.name =
	 * name; this.age = age; this.phone = phone; this.address =address; }
	 */

}