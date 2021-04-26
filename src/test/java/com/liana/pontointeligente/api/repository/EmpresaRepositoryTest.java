package com.liana.pontointeligente.api.repository;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.liana.pontointeligente.api.entities.Empresa;

@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String CNPJ = "514635000100";
	
	@BeforeEach
	public void setUp() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa Exemplo 1");
		empresa.setCnpj(CNPJ);
		this.empresaRepository.save(empresa);
	}
	
	@AfterEach
	public void tearDown() {
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void testBuscaEmpresaPorCnpj() {
		Empresa findByCnpj = this.empresaRepository.findByCnpj(CNPJ);
		Assertions.assertEquals(CNPJ, findByCnpj.getCnpj());
	}
}
