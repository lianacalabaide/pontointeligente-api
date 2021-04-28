package com.liana.pontointeligente.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liana.pontointeligente.api.dto.EmpresaDto;
import com.liana.pontointeligente.api.entities.Empresa;
import com.liana.pontointeligente.api.response.Response;
import com.liana.pontointeligente.api.service.EmpresaService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
@Slf4j
public class EmpresaController {
	
	@Autowired
	private EmpresaService empresaService;
	
	public EmpresaController() {
	}
	
	/**
	 * Consultar uma empresa dado um CNPJ
	 * @param cnpj
	 * @return ResponseEntity<Response<EmpresaDto>>
	 * */
	@GetMapping(value="/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> buscarEmpresaPorCnpj(@PathVariable("cnpj") String cnpj){
		log.info("Buscar empresa por CNPJ{}", cnpj);
		
		Response<EmpresaDto> response = new Response<EmpresaDto>();
		Optional<Empresa> empresa = this.empresaService.buscaPorCnpj(cnpj);
		if (empresa.isEmpty()) {
			log.info("Empresa não encontrada para o CNPJ");
			response.getErrors().add("Empresa não encontrada para o CNPJ "+ cnpj);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterEmpresaDto(empresa.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Converter Empresa para dto
	 * @param empresa
	 * @return EmpresaDto
	 * */
	private EmpresaDto converterEmpresaDto(Empresa empresa) {
		EmpresaDto dto = new EmpresaDto();
		dto.setId(empresa.getId());
		dto.setRazaoSocial(empresa.getRazaoSocial());
		dto.setCnpj(empresa.getCnpj());
		return dto;
	}
}
