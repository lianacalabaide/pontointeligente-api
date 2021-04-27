package com.liana.pontointeligente.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.liana.pontointeligente.api.entities.Lancamento;

public interface LancamentoService {
	/**
	 * Busca paginada de Lançamentos por id de funcionario 
	 * @param funcionarioId
	 * @param pageRequest
	 * @return Page<Funcionario>
	 * */
	
	Page<Lancamento> buscarLancamentoPorIdFuncionarioEPage(Long funcionarioId, PageRequest pageRequest);
	
	/**
	 * Busca lançamento por ID
	 * @param id
	 * @return Optional<Lancamento>
	 * */
	Optional<Lancamento> buscarLancamentoPorId(Long id);
	
	/**
	 * Persistir o lançamento na base de dados
	 * @param lancamento
	 * @return Lancamento
	 * */
	Lancamento persistir(Lancamento lancamento);
	
	/**
	 * Remove o lançamento da base de dados
	 * @param id
	 * */
	void removerLancamento(Long id);
	
	
}
