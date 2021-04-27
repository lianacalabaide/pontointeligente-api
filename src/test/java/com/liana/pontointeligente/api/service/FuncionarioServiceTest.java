package com.liana.pontointeligente.api.service;

import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.liana.pontointeligente.api.entities.Funcionario;
import com.liana.pontointeligente.api.repository.FuncionarioRepository;

@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {

	@MockBean
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	private static final String CPF = "04705336925";
	private static final String email = "lianusca@gmail.com";
	private static final Long ID = 1L;
	
	@BeforeEach
	public void setUp() {
		when(funcionarioRepository.save(any())).thenReturn(new Funcionario());
		when(funcionarioRepository.findByCpf(CPF)).thenReturn(new Funcionario());
		when(funcionarioRepository.findByEmail(email)).thenReturn(new Funcionario());
		when(funcionarioRepository.findById(ID)).thenReturn(Optional.ofNullable(new Funcionario()));
	}
	
	@Test
	public void testPersistFuncionario() {
		Funcionario funcionario = this.funcionarioService.persistir(new Funcionario());
		Assertions.assertNotNull(funcionario);
	}

	@Test
	public void testBuscarPorCpf() {
		Optional<Funcionario> funcionarioPorCpf = this.funcionarioService.buscarFuncionarioPorCpf(CPF);
		Assertions.assertTrue(funcionarioPorCpf.isPresent());
	}
	
	@Test
	public void testBuscarPorEmail() {
		Optional<Funcionario> funcionarioPorEmail = this.funcionarioService.buscarFuncionarioPorEmail(email);
		Assertions.assertTrue(funcionarioPorEmail.isPresent());
	}
	
	@Test
	public void testBuscarPorId() {
		Optional<Funcionario> funcionarioPorId = this.funcionarioService.buscarFuncionarioPorId(ID);
		Assertions.assertTrue(funcionarioPorId.isPresent());
	}
	
	
}
