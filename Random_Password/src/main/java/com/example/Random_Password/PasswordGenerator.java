package com.example.Random_Password;

import java.security.SecureRandom;

public class PasswordGenerator {
	private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	private static final String DIGITS = "0123456789";
	private static final String SYMBOLS = "!@#$%^&*()_+<>?";
	private static final String ALL_CHARCTERS = UPPERCASE + LOWERCASE + DIGITS + SYMBOLS;
	private static final SecureRandom random = new SecureRandom();
	
	// 비밀번호의 길이와 사용할 문자 유형(대문자, 소문자, 숫자, 특수문자)를 받아서 랜덤 비밀번호 생성
	public static String generatePassword(int length, boolean useUppercase, boolean useLowercase, boolean useDigits, boolean useSymbols){
		StringBuilder characterSet = new StringBuilder();
		if (useUppercase) characterSet.append(UPPERCASE);
		if (useLowercase) characterSet.append(LOWERCASE);
		if (useDigits) characterSet.append(DIGITS);
		if (useSymbols) characterSet.append(SYMBOLS);
		
		if (characterSet.length() == 0) throw new IllegalArgumentException("No character sets selected");
		
		StringBuilder password = new StringBuilder(length);
		for (int i =0; i < length; i++) {
			int randomIndex = random.nextInt(characterSet.length());
			password.append(characterSet.charAt(randomIndex));
		}
		
		return password.toString();
	}
	
	// 비밀번호를 생성하는 예제
	public static void main(String[] args) {
		// 예 : 길이 12, 대문자, ㅅ문자 숫자 사용, 특수문자 포함
		String password = generatePassword(12, true, true, true, true);
		System.out.println("Generated Password: " + password);
	}

}
