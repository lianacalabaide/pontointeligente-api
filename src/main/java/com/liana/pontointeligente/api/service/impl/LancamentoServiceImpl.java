package com.liana.pontointeligente.api.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.liana.pontointeligente.api.entities.Lancamento;
import com.liana.pontointeligente.api.repository.LancamentoRepository;
import com.liana.pontointeligente.api.service.LancamentoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LancamentoServiceImpl implements LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Override
	public Page<Lancamento> buscarLancamentoPorIdFuncionarioEPage(Long funcionarioId, PageRequest pageRequest) {
		log.info("Buscar Lancamento por ID e Page {} {}", funcionarioId, pageRequest);
		return lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}

	@Override
	public Optional<Lancamento> buscarLancamentoPorId(Long id) {
		log.info("Buscar Lançamento por ID {}",id);
		return lancamentoRepository.findById(id);
	}

	@Override
	public Lancamento persistir(Lancamento lancamento) {
		log.info("Persistir lancamento {}", lancamento);
		return this.lancamentoRepository.save(lancamento);
	}

	@Override
	public void removerLancamento(Long id) {
		log.info("Remover lancamento {}",id);
		this.lancamentoRepository.deleteById(id);
	}

}
