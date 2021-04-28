package com.liana.pontointeligente.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liana.pontointeligente.api.dto.LancamentoDto;
import com.liana.pontointeligente.api.entities.Funcionario;
import com.liana.pontointeligente.api.entities.Lancamento;
import com.liana.pontointeligente.api.enums.TipoEnum;
import com.liana.pontointeligente.api.response.Response;
import com.liana.pontointeligente.api.service.FuncionarioService;
import com.liana.pontointeligente.api.service.LancamentoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins = "*")
@Slf4j
public class LancamentoController {
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	

	public LancamentoController() {
	}
	
	/**
	 * Retornar a Listagem de lançamentos de um funcionário
	 * @param funcionarioId
	 * @return ResponseEntity<Response<LancamentoDto>>
	 * */
	@GetMapping(value = "/funcionario/{funcionarioId}")
	public ResponseEntity<Response<Page<LancamentoDto>>> listarPorFuncionario(@PathVariable("funcionarioId") Long funcionarioId,
			@RequestParam(value="pag", defaultValue = "0") int pag,
			@RequestParam(value="ord", defaultValue = "id") String ord,
			@RequestParam(value="dir", defaultValue = "DESC") String dir){
		log.info("Buscando lista de lançamentos por funcionário {}, pag {}", funcionarioId, pag);
		
		Response<Page<LancamentoDto>> response = new Response<Page<LancamentoDto>>();
		
		PageRequest pageRequest = PageRequest.of(pag, qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Lancamento> lancamentos = this.lancamentoService.buscarLancamentoPorIdFuncionarioEPage(funcionarioId, pageRequest);
		Page<LancamentoDto> lancDto = lancamentos.map(lanc-> convertLancamentoDto(lanc));
		response.setData(lancDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retornar o lançamento por id
	 * @param id
	 * @return ResponseEntity<Responde<LancamentoDto>>
	 * */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<LancamentoDto>> listarLancamento(@PathVariable("id") Long id){
		log.info("Buscar lançamento por id {}",id);
		Response<LancamentoDto> response = new Response<LancamentoDto>();
		
		Optional<Lancamento> lanc = this.lancamentoService.buscarLancamentoPorId(id);
		if (!lanc.isPresent()) {
			response.getErrors().add("Lançamento não existente.");
			return ResponseEntity.badRequest().body(response); 
		}
		
		response.setData(this.convertLancamentoDto(lanc.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Adicionar lançamento para usuário
	 * @param lancamentoDto
	 * @param result
	 * @return ResponseEntity<Response<LancamentoDto>>
	 * @throws ParseException 
	 * */
	@PostMapping
	public ResponseEntity<Response<LancamentoDto>> adicionar(@Valid @RequestBody LancamentoDto dto, BindingResult result) throws ParseException{
		log.info("Adicionar Lançamento {}",dto);
		
		Response<LancamentoDto> response = new Response<LancamentoDto>();
		validarFuncionario(dto, result);
		Lancamento lancamento = converterDtoParaLancamento(dto, result);
		if(result.hasErrors()) {
			log.error("Erro ao adicionar lançamento");
			result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		Lancamento lancamentoSalvo = this.lancamentoService.persistir(lancamento);
		response.setData(convertLancamentoDto(lancamentoSalvo));
		return ResponseEntity.ok(response);
	}
	
	
	/**
	 * Atualizar um lançamento existente
	 * @param id
	 * @param lancamentoDto
	 * @param result
	 * @return ResponseEntity<Response<LancamentoDto>>
	 * @throws ParseException 
	 * */
	@PutMapping("/{id}")
	public ResponseEntity<Response<LancamentoDto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody LancamentoDto dto, BindingResult result) throws ParseException{
		log.error("Atualizando lançamento {} dto {}", id, dto);
		Response<LancamentoDto> response = new Response<LancamentoDto>();
		
		validarFuncionario(dto, result);
		dto.setId(Optional.of(id));
		Lancamento lancamento = converterDtoParaLancamento(dto, result);
		
		if(result.hasErrors()) {
			log.error("Erro ao adicionar lançamento");
			result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Lancamento lancamentoSalvo = this.lancamentoService.persistir(lancamento);
		response.setData(this.convertLancamentoDto(lancamentoSalvo));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Deleta um lancamento
	 * @param id
	 * @return ResponseEntity<Response<String>>
	 * */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> deletaLancamento(@PathVariable("id") Long id){
		log.error("Removendo lancamento {}", id);
		Response<String> response = new Response<String>();
		Optional<Lancamento> lancamentoPorId = this.lancamentoService.buscarLancamentoPorId(id);
		if(!lancamentoPorId.isPresent()) {
			response.getErrors().add("Id de lançamento inexistente na base de dados");
			return ResponseEntity.badRequest().body(response);
		}
		this.lancamentoService.removerLancamento(id);
		return ResponseEntity.ok(response);
	}
		
	/**
	 * Converter dto para Lancamento
	 * @param lancamentoDto
	 * @return Lancamento
	 * @throws ParseException 
	 * */
	private Lancamento converterDtoParaLancamento(LancamentoDto dto, BindingResult result) throws ParseException {
		Lancamento lancamento = new Lancamento();
		if (dto.getId().isPresent()) {
			Optional<Lancamento> lanc = this.lancamentoService.buscarLancamentoPorId(dto.getId().get());
			if(lanc.isPresent()) {
				lancamento = lanc.get();
			} else {
				result.addError(new ObjectError("lancamento", "Id de lançamento inválido"));
			}
		} else {
			lancamento.setFuncionario(new Funcionario());
			lancamento.setFuncionario(this.funcionarioService.buscarFuncionarioPorId(Long.valueOf(dto.getFuncionarioId())).get());
		}

		
		lancamento.setData(dateFormat.parse(dto.getData()));
		lancamento.setDescricao(dto.getDescricao());
		lancamento.setLocalizacao(dto.getLocalizacao());
		if(EnumUtils.isValidEnum(TipoEnum.class, dto.getTipo())) {
			lancamento.setTipo(TipoEnum.valueOf(dto.getTipo()));
		} else {
			result.addError(new ObjectError("lancamento", "Tipo de lançamento inválido"));
		}
		return lancamento;
	}

	/**
	 * Validar se funcionário do lançamento existe na base de dados
	 *  @param lancamentoDto
	 *  @param result
	 * */
	private void validarFuncionario(LancamentoDto dto, BindingResult result) {
		if (Objects.isNull(dto.getFuncionarioId())) {
			result.addError(new ObjectError("lancamento", "Funcionario não informado"));
			return;
		}
		
		Optional<Funcionario> funcionario = this.funcionarioService.buscarFuncionarioPorId(Long.valueOf(dto.getFuncionarioId()));
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("lancamento", "Funcionario não existente na base de dados"));
		}
		
	}

	/**
	 * Converter Lancamento em DTO
	 * @param lancamento
	 * @return lancamentoDto
	 * */
	private LancamentoDto convertLancamentoDto(Lancamento lancamento) {
		LancamentoDto dto = new LancamentoDto();
		dto.setData(dateFormat.format(lancamento.getData()));
		dto.setDescricao(lancamento.getDescricao());
		dto.setFuncionarioId(lancamento.getFuncionario().getId().toString());
		dto.setId(Optional.of(lancamento.getId()));
		return dto;
	}
}
