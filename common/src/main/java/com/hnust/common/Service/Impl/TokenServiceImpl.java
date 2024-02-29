package com.hnust.common.Service.Impl;

import com.hnust.common.Mode.Base.Token;
import com.hnust.common.Service.Interface.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenServiceImpl  implements TokenService {

	public  static Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	private static long expirationTime=3600*6*1000;

	@Override
	public Token generate(String module, Long index, String id) {
		Token token = new Token(expirationTime,index);
		token.setModule(module);
		token.setToken(generateJwt(id));
		return token;
	}

	@Override
	public boolean check(String token) {
		return validateJwt(token);
	}

	@Override
	public String decode(String token) {
		if(token==null){
			throw new RuntimeException("必要参数不能为空");
		}
		return decodeJwt(token).getSubject();
	}


	public  String generateJwt(String subject) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + expirationTime);
		String res= Jwts.builder()
				.setSubject(subject)
				.setIssuedAt(now)
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				//.serializeToJsonWith(new GsonSerializer<>(new Gson()))
				.compact();
		return res;
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
