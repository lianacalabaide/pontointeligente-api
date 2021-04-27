package com.liana.pontointeligente.api.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.liana.pontointeligente.api.entities.Lancamento;
import com.liana.pontointeligente.api.repository.LancamentoRepository;

@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {
	@MockBean
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@BeforeEach
	public void setUp() {
		when(lancamentoRepository.findByFuncionarioId(1L, PageRequest.of(0, 10))).thenReturn(new PageImpl<Lancamento>(new ArrayList<Lancamento>()));
		when(lancamentoRepository.findById(1L)).thenReturn(Optional.ofNullable(new Lancamento()));
		when(lancamentoRepository.save(any())).thenReturn(new Lancamento());
	}
	
	@Test
	public void testBuscarPorfuncionarioIdAndPage() {
		Page<Lancamento> funcionarioEPage = lancamentoService.buscarLancamentoPorIdFuncionarioEPage(1L, PageRequest.of(0,10));
		Assertions.assertNotNull(funcionarioEPage);
	}
	
	@Test
	public void testBuscarLancamentoPorId() {
		Optional<Lancamento> lancamentoPorId = lancamentoService.buscarLancamentoPorId(1L);
		Assertions.assertNotNull(lancamentoPorId);
	}
	
	@Test
	public void testPersistirLancamento() {
		Lancamento lancamento = lancamentoService.persistir(new Lancamento());
		Assertions.assertNotNull(lancamento);
	}
}
