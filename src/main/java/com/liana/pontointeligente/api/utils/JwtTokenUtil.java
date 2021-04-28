package com.liana.pontointeligente.api.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
	public static final String CLAIM_KEY_USERNAME = "sub";
	public static final String CLAIM_KEY_ROLE = "role";
	public static final String CLAIM_KEY_CREATED = "created";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	/**
	 * Obter o username(e-mail) obtido no Token
	 * 
	 * @param token
	 * @return String
	 */
	public String getUsernameFromToke(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	/**
	 * Obter data de expiração obtida do token
	 * 
	 * @param token
	 * @return Date
	 */
	public Date getExpirationFromToke(String token) {
		Date expiration;
		try {
			Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	/**
	 * Criar um novo token
	 * 
	 * @param token
	 * @return String
	 */
	public String refreshToken(String token) {
		String refreshedToken;
		try {
			Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = gerarToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	/**
	 * Realiza o parse do Token JWT para extrair as informações contidas no corpo
	 * dele
	 * 
	 * @param token
	 * @return Claims
	 */
	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}

		return claims;
	}

	/**
	 * Verifica e retorna se um token JWT é valido
	 * 
	 * @param token
	 * @return boolean
	 */
	public boolean tokenValido(String token) {
		return !tokenExpirado(token);
	}

	/**
	 * Retorna um novo token JWT baseado nos dados do usuário
	 * 
	 * @param userDetails
	 * @return String
	 */
	public String obterToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		userDetails.getAuthorities().forEach(auth -> claims.put(CLAIM_KEY_ROLE, auth.getAuthority()));
		claims.put(CLAIM_KEY_CREATED, new Date());
		return gerarToken(claims);
	}

	/**
	 * Verifica se o token está expirado
	 * 
	 * @param tooken
	 * @return boolean
	 */
	private boolean tokenExpirado(String token) {
		Date dataExpiracao = this.getExpirationFromToke(token);
		if (Objects.isNull(dataExpiracao))
			return false;

		return dataExpiracao.before(new Date());
	}

	/**
	 * Gerar data de expiração
	 * 
	 * @return Date
	 */
	private Date gerarDataExpiracao() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	/**
	 * Gerar novo token JWT contendo os dados (claims) fornecidos
	 * 
	 * @param claims
	 * @return String
	 */
	private String gerarToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(gerarDataExpiracao())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
}
