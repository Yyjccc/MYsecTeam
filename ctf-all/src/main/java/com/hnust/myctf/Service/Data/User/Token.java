package com.hnust.myctf.Service.Data.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;

import java.security.Key;
import java.util.Date;

@Data
public class  Token {

	private String jwt;


	public  static Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	//生成jwt
	public  void generateJwt(String subject,long expirationTime) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + expirationTime);

		String res=Jwts.builder()
				.setSubject(subject)
				.setIssuedAt(now)
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				//.serializeToJsonWith(new GsonSerializer<>(new Gson()))
				.compact();
		this.setJwt(res);

	}

	//校验jwt


	public static boolean validateJwt(String jwt) {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(jwt)
					.getBody();
			// 在这里可以根据需要检查其他声明或条件

			return true;
		} catch (Exception e) {
			// 处理异常，例如JWT过期或签名不匹配
			return false;
		}
	}
	//解密jwt
	public static Claims decodeJwt(String jwtToken) {
		try {
			return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
		} catch (Exception e) {
			// 处理解密失败的情况
			e.printStackTrace();
			return null;
		}
	}
}
