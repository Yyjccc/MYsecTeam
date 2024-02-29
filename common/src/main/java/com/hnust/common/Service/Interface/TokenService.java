package com.hnust.common.Service.Interface;

import com.hnust.common.Mode.Base.Token;

public interface TokenService {
	Token generate(String module, Long index,String id);

	boolean check(String token);

	String decode(String token);
}
