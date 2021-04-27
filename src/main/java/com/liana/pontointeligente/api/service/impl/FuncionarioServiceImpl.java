package com.liana.pontointeligente.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liana.pontointeligente.api.entities.Funcionario;
import com.liana.pontointeligente.api.repository.FuncionarioRepository;
import com.liana.pontointeligente.api.service.FuncionarioService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FuncionarioServiceImpl implements FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Override
	public Funcionario persistir(Funcionario funcionario) {
		log.info("Persistindo funcionário {}", funcionario);
		return funcionarioRepository.save(funcionario);
	}

	@Override
	public Optional<Funcionario> buscarFuncionarioPorCpf(String cpf) {
		log.info("Consulta funcionário por cpf {}", cpf);
		return Optional.ofNullable(funcionarioRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscarFuncionarioPorEmail(String email) {
		log.info("Consulta funcionário por email {}", email);
		return Optional.ofNullable(funcionarioRepository.findByEmail(email));
	}

	@Override
	public Optional<Funcionario> buscarFuncionarioPorId(Long id) {
		log.info("Consulta funcionário por id {}", id);
		return funcionarioRepository.findById(id);
	}

}
