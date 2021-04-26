package com.liana.pontointeligente.api.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.liana.pontointeligente.api.entities.Empresa;
import com.liana.pontointeligente.api.repository.EmpresaRepository;

@SpringBootTest
@ActiveProfiles("test")
public class EmpresaServiceTest {

	@MockBean
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private EmpresaService empresaService;
	
	private static final String CNPJ = "514635000100";
	
	@BeforeEach
	public void setUp() {
		when(empresaRepository.findByCnpj(CNPJ)).thenReturn(new Empresa());
		when(empresaRepository.save(any())).thenReturn(new Empresa());
	}
	
	@Test
	public void testBuscarEmpresaPorCnpj() {
		Optional<Empresa> buscaPorCnpj = this.empresaService.buscaPorCnpj(CNPJ);
		Assertions.assertTrue(buscaPorCnpj.isPresent());
	}
	
	@Test
	public void testPersistirEmpresa() {
		Empresa empresa = this.empresaService.persistir(new Empresa());
		Assertions.assertNotNull(empresa);
	}
	
	
}
