package com.liana.pontointeligente.api.service;

import java.util.Optional;

import com.liana.pontointeligente.api.entities.Funcionario;

public interface FuncionarioService {
	/**
	 * Persiste um funcionário na base de dados
	 * @param Funcionario
	 * @return Funcionario
	 * */
	Funcionario persistir(Funcionario funcionario);
	
	/**
	 * Busca um funcionário por CPF
	 * @para cpf
	 * @return Optional<Funcionario>
	 * */
	Optional<Funcionario> buscarFuncionarioPorCpf(String cpf);

	/**
	 * Busca um funcionário por e-mail
	 * @para email
	 * @return Optional<Funcionario>
	 * */
	Optional<Funcionario> buscarFuncionarioPorEmail(String email);	
	
	/**
	 * Busca um funcionário por id
	 * @para id
	 * @return Optional<Funcionario>
	 * */
	Optional<Funcionario> buscarFuncionarioPorId(Long id);	
	
}
