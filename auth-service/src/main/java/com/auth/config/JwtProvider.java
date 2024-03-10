package com.auth.config;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth.modal.CustomUser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Value("${app.jwtExpiration}")
	private long jwtExpiration;

	public String generateJwtToken(Authentication authentication) {
        CustomUser userPrincipal = (CustomUser) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject(String.valueOf(userPrincipal.getId()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}
	
	public UUID getUserIdFromJwtToken(String token) {
        String subject = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
		return UUID.fromString(subject);
    }

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken, HttpServletRequest httpServletRequest) {
		String ex = "";
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature -> Message: {} ", e);
			ex = "Invalid JWT signature";
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token -> Message: {}", e);
			ex = "Invalid JWT token";
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT token -> Message: {}", e);
			ex = "Expired JWT token";
		} catch (UnsupportedJwtException e) {
			logger.error("Unsupported JWT token -> Message: {}", e);
			ex = "Unsupported JWT token";
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty -> Message: {}", e);
			ex = "JWT claims string is empty";
		}
		httpServletRequest.setAttribute("jwtException", ex);
		return false;
	}
}
