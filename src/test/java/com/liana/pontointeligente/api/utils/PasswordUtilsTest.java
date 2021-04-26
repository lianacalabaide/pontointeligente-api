package com.liana.pontointeligente.api.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PasswordUtilsTest {

	private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	@Test
	public void testPasswordNull() {
		String gerarBCrypt = PasswordUtils.gerarBCrypt(null);
		Assertions.assertNull(gerarBCrypt);
	}
	
	@Test
	public void testPassWordNotNull (){
		String hash = PasswordUtils.gerarBCrypt("123");
		Assertions.assertTrue(bcrypt.matches("123", hash));
	}
}
