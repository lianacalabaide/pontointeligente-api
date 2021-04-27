package com.liana.pontointeligente.api.controller;

import java.security.NoSuchAlgorithmException;

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

import com.liana.pontointeligente.api.dto.CadastroPJDto;
import com.liana.pontointeligente.api.entities.Empresa;
import com.liana.pontointeligente.api.entities.Funcionario;
import com.liana.pontointeligente.api.enums.PerfilEnum;
import com.liana.pontointeligente.api.response.Response;
import com.liana.pontointeligente.api.service.EmpresaService;
import com.liana.pontointeligente.api.service.FuncionarioService;
import com.liana.pontointeligente.api.utils.PasswordUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin(origins = "*")
@Slf4j
public class CadastroPJController {

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private FuncionarioService funcionarioService;

	public CadastroPJController() {
	}

	/**
	 * Cadastra pessoa Jurídica no sistema
	 * 
	 * @param cadastroPJDto
	 * @param result
	 * @return ResponseEntity<Responde<CadastroPJDto>>
	 * @throws NoSuchAlgorithmException
	 */

	@PostMapping
	public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJdto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando PJ {}", cadastroPJdto);

		Response<CadastroPJDto> response = new Response<CadastroPJDto>();

		validarDadosExistentes(cadastroPJdto, result);
		Empresa empresa = this.converterDtoParaEmpresa(cadastroPJdto);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPJdto, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados do cadastro PJ {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Empresa empresaSalva = this.empresaService.persistir(empresa);
		funcionario.setEmpresa(empresaSalva);
		this.funcionarioService.persistir(funcionario);
		response.setData(this.converterCadastroPJDto(funcionario));
		return ResponseEntity.ok(response);
	}

	/**
	 * 
	 * Converter Funcionario para CadastroPJDto
	 * @param funcionario
	 * @return CadastroPJDto
	 * */
	private CadastroPJDto converterCadastroPJDto(Funcionario funcionario) {
		CadastroPJDto cadastroPJdto = new CadastroPJDto();
		cadastroPJdto.setId(funcionario.getId());
		cadastroPJdto.setNome(funcionario.getNome());
		cadastroPJdto.setCpf(funcionario.getCpf());
		cadastroPJdto.setEmail(funcionario.getEmail());
		cadastroPJdto.setCnpj(funcionario.getEmpresa().getCnpj());
		cadastroPJdto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		return cadastroPJdto;
	}

	/**
	 * Converter DTO para Funcionario
	 * @param cadastroPJdto
	 * @param result
	 * @return Funcionario
	 * */
	private Funcionario converterDtoParaFuncionario(CadastroPJDto cadastroPJdto, BindingResult result) throws NoSuchAlgorithmException{
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(cadastroPJdto.getCpf());
		funcionario.setNome(cadastroPJdto.getNome());
		funcionario.setEmail(cadastroPJdto.getEmail());
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJdto.getSenha()));
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		return funcionario;
	}

	/**
	 * Converter DTO para Empresa
	 * @param cadastroPJdto
	 * @return Empresa
	 * */
	private Empresa converterDtoParaEmpresa(CadastroPJDto cadastroPJdto) {
		Empresa empresa = new Empresa();
		empresa.setCnpj(cadastroPJdto.getCnpj());
		empresa.setRazaoSocial(cadastroPJdto.getRazaoSocial());
		return empresa;
	}

	
	/**
	 * Verificar se Empresa ou Usuário já existentes na base de dados
	 * */
	private void validarDadosExistentes(CadastroPJDto cadastroPJdto, BindingResult result) {
		this.empresaService.buscaPorCnpj(cadastroPJdto.getCnpj()).ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existente")));

		this.funcionarioService.buscarFuncionarioPorCpf(cadastroPJdto.getCpf()).ifPresent(funcionario -> result.addError(new ObjectError("funcionario", "Funcionário já existente com este CPF")));

		this.funcionarioService.buscarFuncionarioPorEmail(cadastroPJdto.getEmail()).ifPresent(funcionario -> result.addError(new ObjectError("funcionario", "Funcionário já existente com este E-mail")));
		
	}

}
