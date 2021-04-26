package com.liana.pontointeligente.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liana.pontointeligente.api.entities.Empresa;
import com.liana.pontointeligente.api.repository.EmpresaRepository;
import com.liana.pontointeligente.api.service.EmpresaService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmpresaServiceImpl implements EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Override
	public Optional<Empresa> buscaPorCnpj(String cnpj) {
		log.info("Buscar empresa por CNPJ {}", cnpj);
		return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
	}

	@Override
	public Empresa persistir(Empresa empresa) {
		log.info("Salvar empresa no banco de dados {}", empresa);
		return this.empresaRepository.save(empresa);
	}

}
