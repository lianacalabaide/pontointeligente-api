package com.liana.pontointeligente.api.service;

import java.util.Optional;

import com.liana.pontointeligente.api.entities.Empresa;

public interface EmpresaService {
	/**
	 * Retorna uma empresa dado um CNPJ
	 * @param cnpj
	 * @return Optional<Empresa>
	 * */
	
	Optional<Empresa> buscaPorCnpj(String cnpj);
	
	
	/**
	 * Cadastrar uma nova empresa
	 * @param Empresa
	 * @return Empresa
	 * */
	Empresa persistir(Empresa empresa);
}
