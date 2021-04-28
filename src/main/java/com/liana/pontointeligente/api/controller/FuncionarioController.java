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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liana.pontointeligente.api.dto.FuncionarioDto;
import com.liana.pontointeligente.api.entities.Funcionario;
import com.liana.pontointeligente.api.response.Response;
import com.liana.pontointeligente.api.service.FuncionarioService;
import com.liana.pontointeligente.api.utils.PasswordUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
@Slf4j
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;

	public FuncionarioController() {
	}

	/**
	 * Atualizar os dados de um funcionário
	 * 
	 * @param id
	 * @return ResponseEntity<Response<FuncionarioDto>>
	 * @throws NoSuchAlgorithmException 
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult result) throws NoSuchAlgorithmException {
		log.info("Atualização de funcionário {}", id);
		Response<FuncionarioDto> response = new Response<FuncionarioDto>();

		Optional<Funcionario> funcionario = funcionarioService.buscarFuncionarioPorId(id);
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionário", "Funcionário não encontrado na base de dados"));
		}

		this.atualizarDadosFuncionario(funcionario.get(), funcionarioDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando Funcionário: {}", result.getAllErrors());
			result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		this.funcionarioService.persistir(funcionario.get());
		response.setData(this.converterFuncionarioParaDto(funcionario.get()));

		return ResponseEntity.ok(response);
	}

	/**
	 * Converter Funcionario para Dto
	 * 
	 * @param funcionario
	 * @param FuncionarioDto
	 */

	private FuncionarioDto converterFuncionarioParaDto(Funcionario funcionario) {
		FuncionarioDto dto = new FuncionarioDto();
		dto.setId(funcionario.getId());
		dto.setNome(funcionario.getNome());
		dto.setEmail(funcionario.getEmail());
		dto.setSenha(Optional.of(funcionario.getSenha()));
		if (Objects.nonNull(funcionario.getValorHora()))
			dto.setValorHora(Optional.of(funcionario.getValorHora().toString()));

		if (Objects.nonNull(funcionario.getQtdHorasAlmoco()))
			dto.setQtdHoraAlmoco(Optional.of(funcionario.getQtdHorasAlmoco().toString()));

		if (Objects.nonNull(funcionario.getQtdHorasTrabalhadasDia()))
			dto.setQtdHorasTrabalhadasDia(Optional.of(funcionario.getQtdHorasTrabalhadasDia().toString()));

		return dto;
	}

	/**
	 * Atualizar dados do funcionário
	 * 
	 * @param funcionario
	 * @param funcionarioDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 *
	 */
	private void atualizarDadosFuncionario(Funcionario funcionario, @Valid FuncionarioDto funcionarioDto,
			BindingResult result) throws NoSuchAlgorithmException {
		funcionario.setNome(funcionarioDto.getNome());

		if (!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
			this.funcionarioService.buscarFuncionarioPorEmail(funcionarioDto.getEmail())
					.ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já existente")));
		}

		funcionarioDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
		funcionarioDto.getQtdHoraAlmoco()
				.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
		funcionarioDto.getQtdHorasTrabalhadasDia().ifPresent(
				qtdHorasTrabalhadasDia -> funcionario.setQtdHorasTrabalhadasDia(Float.valueOf(qtdHorasTrabalhadasDia)));
		funcionarioDto.getSenha().ifPresent(senha -> funcionario.setSenha(PasswordUtils.gerarBCrypt(senha)));
	}
}
