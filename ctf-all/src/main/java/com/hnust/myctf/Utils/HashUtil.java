package com.hnust.myctf.Utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {

	private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
	private static final String DIGIT_CHARACTERS = "0123456789";

	private static final  String SYMBOL_CHARACTERS="!~@#$%^&*()-+_=[]{}?><";

	private static final int LENGTH = 10;
	// 生成随机盐并对密码进行哈希加密
	public static String hashPassword(String rawPassword, String salt) {
		String combined = rawPassword + "{" + salt + "}";
		return hashPassword(combined);
	}
	public static String hashPassword(String password) {
		String passwordWithSalt = password;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = messageDigest.digest(passwordWithSalt.getBytes());
			StringBuilder hashStringBuilder = new StringBuilder();

			for (byte hashByte : hashBytes) {
				hashStringBuilder.append(String.format("%02x", hashByte));
			}

			return hashStringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			// Handle the exception according to your needs
			return null;
		}
	}



	// 生成随机盐
//	public static String generateSalt() {
//		// 在实际应用中，可以使用更安全的方式生成随机盐
//		return Long.toHexString(Double.doubleToLongBits(Math.random()));
//	}


	public static String generateSalt() {
		SecureRandom secureRandom = new SecureRandom();
		StringBuilder randomStringBuilder = new StringBuilder();

		// 添加至少一个大写字母
		randomStringBuilder.append(UPPERCASE_CHARACTERS.charAt(secureRandom.nextInt(UPPERCASE_CHARACTERS.length())));


		//添加至少一个符号
		randomStringBuilder.append(SYMBOL_CHARACTERS.charAt(secureRandom.nextInt(SYMBOL_CHARACTERS.length())));

		// 添加至少一个数字
		randomStringBuilder.append(DIGIT_CHARACTERS.charAt(secureRandom.nextInt(DIGIT_CHARACTERS.length())));

		// 添加至少一个小写字母
		randomStringBuilder.append(LOWERCASE_CHARACTERS.charAt(secureRandom.nextInt(LOWERCASE_CHARACTERS.length())));


		randomStringBuilder.append(SYMBOL_CHARACTERS.charAt(secureRandom.nextInt(SYMBOL_CHARACTERS.length())));
		// 添加其他字符
		for (int i = 3; i < LENGTH; i++) {
			int randomIndex = secureRandom.nextInt(UPPERCASE_CHARACTERS.length() +
					LOWERCASE_CHARACTERS.length() +
					DIGIT_CHARACTERS.length());
			if (randomIndex < UPPERCASE_CHARACTERS.length()) {
				randomStringBuilder.append(UPPERCASE_CHARACTERS.charAt(randomIndex));
			} else if (randomIndex < UPPERCASE_CHARACTERS.length() + LOWERCASE_CHARACTERS.length()) {
				randomStringBuilder.append(LOWERCASE_CHARACTERS.charAt(randomIndex - UPPERCASE_CHARACTERS.length()));
			} else {
				randomStringBuilder.append(DIGIT_CHARACTERS.charAt(randomIndex - UPPERCASE_CHARACTERS.length() - LOWERCASE_CHARACTERS.length()));
			}
		}
		return randomStringBuilder.toString();
	}
}
