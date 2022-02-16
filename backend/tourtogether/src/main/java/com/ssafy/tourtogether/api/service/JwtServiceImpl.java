package com.ssafy.tourtogether.api.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {

	public static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

	private static final String SALT = "tourtogether";
	private static final int EXPIRE_HOURS = 24;

	@Override
	public <T> String create(String key, T data, String subject) {
		String token = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("regDate", System.currentTimeMillis())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * EXPIRE_HOURS)).setSubject(subject)
				.claim(key, data).signWith(SignatureAlgorithm.HS256, this.generateKey()).compact();
		System.out.println("@@@@@토큰 발급: "+token);
		return token;
	}

	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if (logger.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				logger.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}

		return key;
	}

	@Override
	public boolean isUsable(String token) {
		try {
			Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

//	@Override
//	public Map<String, Object> get(String key) {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//				.getRequest();
//		String jwt = request.getHeader("access-token");
//		Jws<Claims> claims = null;
//		try {
//			claims = Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(jwt);
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		}
//		Map<String, Object> value = claims.getBody();
//		logger.info("value : {}", value);
//		return value;
//	}
//
//	@Override
//	public String getUserId() {
//		return (String) this.get("user").get("userid");
//	}
}
