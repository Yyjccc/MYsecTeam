package org.hnust.MYSec.Utils;

public class test {
	public static void main(String[] args) {
		String password="";
		String hash=HashUtil.hashPassword(password);
		System.out.println(hash);
		System.out.println(HashUtil.generateSalt());

	}
}
