package com.example.Random_Password;

import java.security.SecureRandom;

public class SecureRandomExample {

	public static void main(String[] args) {
		//SecureRandom 객체 생성
		SecureRandom secureRandom = new SecureRandom();
		
		//1. 0~99 사이의 정수 생성
		int randomInt = secureRandom.nextInt(100); //값 n을 전달하면 0에서 n-1 사이의 숫자를 반환
		System.out.println("Random Integer (0-99): " + randomInt);
		
		//2. 0.0과 1.0 사이의 부동 소수점 수 생성
		double randomDouble = secureRandom.nextDouble(); //부동 소수점 난수
		System.out.println("Random Double (0.0`1.0): " + randomDouble);
		
		//3. 임의의 바이트 배열 생성(예 : 16바이트 랜덤 배열, 주로 토큰 생성에 사용)
		byte[] randomBytes = new byte[16];
		secureRandom.nextBytes(randomBytes);;
		System.out.print("Random Bytes: ");;
		for(byte b : randomBytes) {
			System.out.printf("%02x ", b); //각 바이트를 16진수로 출력
		}
		System.out.println();
		
		//4. 암호학적 안전한 비밀번호 생성
		String password = generateSecurePassword(12, secureRandom);
		System.out.println("Secure Random Password: " + password);
	}
	
	//암호학적으로 안전한 비밀번호 생성 메서드
	public static String generateSecurePassword(int length, SecureRandom secureRandom) {
		String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+<>?";
		StringBuilder password = new StringBuilder(length);
		
		for(int i =0; i < length; i++){
			int randomIndex = secureRandom.nextInt(characterSet.length());
			password.append(characterSet.charAt(randomIndex));
		}
		
		return password.toString();
	}
}
