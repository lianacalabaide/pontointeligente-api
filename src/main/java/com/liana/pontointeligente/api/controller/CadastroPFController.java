package com.liana.pontointeligente.api.controller;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liana.pontointeligente.api.dto.CadastroPFDto;
import com.liana.pontointeligente.api.entities.Empresa;
import com.liana.pontointeligente.api.entities.Funcionario;
import com.liana.pontointeligente.api.enums.PerfilEnum;
import com.liana.pontointeligente.api.response.Response;
import com.liana.pontointeligente.api.service.EmpresaService;
import com.liana.pontointeligente.api.service.FuncionarioService;
import com.liana.pontointeligente.api.utils.PasswordUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cadastrar-pf")
@CrossOrigin(origins = "*")
@Slf4j
public class CadastroPFController {

	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private EmpresaService empresaService;
	
	public CadastroPFController() {
	}
	
	/**
	 * Cadastra pessoa Física no sistema
	 * 
	 * @param cadastroPFDto
	 * @param result
	 * @return ResponseEntity<Responde<CadastroPFDto>>
	 * @throws NoSuchAlgorithmException
	 */

	@PostMapping
	public ResponseEntity<Response<CadastroPFDto>> cadastrar(@Valid @RequestBody CadastroPFDto cadastroPFdto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando PJ {}", cadastroPFdto);

		Response<CadastroPFDto> response = new Response<CadastroPFDto>();

		validarDadosExistentes(cadastroPFdto, result);
		Empresa empresa = this.converterDtoParaEmpresa(cadastroPFdto);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPFdto, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados do cadastro PF {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		funcionario.setEmpresa(empresa);
		this.funcionarioService.persistir(funcionario);
		response.setData(this.converterCadastroPFDto(funcionario));
		return ResponseEntity.ok(response);
	}

	/**
	 * Converter funcionario para DTO
	 * @param funcionario
	 * @return CadastroPFDto
	 * */
	private CadastroPFDto converterCadastroPFDto(Funcionario funcionario) {
		CadastroPFDto dto = new CadastroPFDto();
		dto.setId(funcionario.getId());
		dto.setCpf(funcionario.getCpf());
		dto.setNome(funcionario.getNome());
		dto.setCnpj(funcionario.getEmpresa().getCnpj());
		dto.setEmail(funcionario.getEmail());
		if (Objects.nonNull(funcionario.getQtdHorasAlmoco()))
			dto.setQtdHorasAlmoco(Optional.ofNullable(funcionario.getQtdHorasAlmoco().toString()));
	
		if(Objects.nonNull(funcionario.getQtdHorasTrabalhadasDia()))
			dto.setQtdHorasTrabalhadasDia(Optional.of (funcionario.getQtdHorasTrabalhadasDia().toString()));
		
		if(Objects.nonNull(funcionario.getValorHora()))
			dto.setValorHora(Optional.ofNullable(funcionario.getValorHora().toString()));
		
		return dto;
	}

	/**
	 * Converter dto para funcionario
	 * @param cadastroPFdto
	 * @param result
	 * @return Funcionario
	 * */
	private Funcionario converterDtoParaFuncionario(CadastroPFDto cadastroPFdto, BindingResult result) throws NoSuchAlgorithmException{
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(cadastroPFdto.getCpf());
		funcionario.setNome(cadastroPFdto.getNome());
		funcionario.setEmail(cadastroPFdto.getEmail());
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPFdto.getSenha()));
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);		
		cadastroPFdto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
		cadastroPFdto.getQtdHorasAlmoco().ifPresent(horaAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(horaAlmoco)));
		cadastroPFdto.getQtdHorasTrabalhadasDia().ifPresent(horaDia -> funcionario.setQtdHorasTrabalhadasDia(Float.valueOf(horaDia)));
		
		return funcionario;
	}

	/**
	 * Converter dto para Empresa
	 * @param cadastroPFdto
	 * @return Empresa
	 * */
	private Empresa converterDtoParaEmpresa(CadastroPFDto cadastroPFdto) {
		Empresa empresa = this.empresaService.buscaPorCnpj(cadastroPFdto.getCnpj()).orElse(null);
		return empresa;
	}

	/**
	 * Validar se os dados já existen na base de dados
	 * @param cadastroPFdtop
	 * @param result
	 * */
	private void validarDadosExistentes(CadastroPFDto cadastroPFdto, BindingResult result) {
		if (this.empresaService.buscaPorCnpj(cadastroPFdto.getCnpj()).isEmpty()) {
			result.addError(new ObjectError("empresa", "CNPJ inexistente"));
		}
		
		this.funcionarioService.buscarFuncionarioPorCpf(cadastroPFdto.getCpf()).ifPresent(funcionario -> result.addError(new ObjectError("funcionario", "CPF existente")));
		this.funcionarioService.buscarFuncionarioPorEmail(cadastroPFdto.getEmail()).ifPresent(funcionario -> result.addError(new ObjectError("funcionario", "Email existente")));
	}

	
}
