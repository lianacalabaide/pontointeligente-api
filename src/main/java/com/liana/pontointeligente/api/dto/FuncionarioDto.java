package com.liana.pontointeligente.api.dto;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class FuncionarioDto {
	private Long id;
	private String nome;
	private String email;
	private Optional<String> senha = Optional.empty();
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qtdHorasTrabalhadasDia = Optional.empty();
	private Optional<String> qtdHoraAlmoco = Optional.empty();

	public FuncionarioDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Nome não pode ser vazio")
	@Length(min = 3, max = 200, message = "Nome deve ter entre 3 a 200 caracteres")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotEmpty(message = "E-mail não pode ser vazio")
	@Email(message = "E-mail inválido")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Optional<String> getSenha() {
		return senha;
	}

	public void setSenha(Optional<String> senha) {
		this.senha = senha;
	}

	public Optional<String> getValorHora() {
		return valorHora;
	}

	public void setValorHora(Optional<String> valorHora) {
		this.valorHora = valorHora;
	}

	public Optional<String> getQtdHorasTrabalhadasDia() {
		return qtdHorasTrabalhadasDia;
	}

	public void setQtdHorasTrabalhadasDia(Optional<String> qtdHorasTrabalhadasDia) {
		this.qtdHorasTrabalhadasDia = qtdHorasTrabalhadasDia;
	}

	public Optional<String> getQtdHoraAlmoco() {
		return qtdHoraAlmoco;
	}

	public void setQtdHoraAlmoco(Optional<String> qtdHoraAlmoco) {
		this.qtdHoraAlmoco = qtdHoraAlmoco;
	}

	@Override
	public String toString() {
		return "FuncionarioDto [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", valorHora"
				+ valorHora + ", qtdHorasTrabalhadasDia=" + qtdHorasTrabalhadasDia + ", qtdHorasAlmoco="
				+ qtdHoraAlmoco+"]";
	}
}
