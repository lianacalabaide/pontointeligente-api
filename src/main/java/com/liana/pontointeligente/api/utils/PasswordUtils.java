package com.liana.pontointeligente.api.utils;

import java.util.Objects;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordUtils {
	public PasswordUtils() {
	}
	
	/**
	 * Gera um hash utilizando BCrypt
	 * @param senha
	 * @return string
	 */
	public static String gerarBCrypt(String senha) {
		if (Objects.isNull(senha)) {
			return senha;
		}
		
		log.info("Gerar bCrypt");
		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		return bCryptEncoder.encode(senha);
	}
}
