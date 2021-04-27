package com.liana.pontointeligente.api.dto;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

public class CadastroPFDto {

	private Long id;
	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qtdHorasTrabalhadasDia = Optional.empty();
	private Optional<String> qtdHorasAlmoco = Optional.empty();
	private String cnpj;

	public CadastroPFDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter de 3 a 200 caracteres")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotEmpty(message = "E-mail não pode ser vazio.")
	@Length(min = 3, max = 200, message = "E-mail deve conter de 3 a 200 caracteres")
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "CPF não pode ser vazio.")
	@CPF(message = "CPF inválido")
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@NotEmpty(message = "Senha não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Senha deve conter de 3 a 200 caracteres")
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
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

	public Optional<String> getQtdHorasAlmoco() {
		return qtdHorasAlmoco;
	}

	public void setQtdHorasAlmoco(Optional<String> qtdHorasAlmoco) {
		this.qtdHorasAlmoco = qtdHorasAlmoco;
	}

	@NotEmpty(message = "CNPJ não pode ser vazio.")
	@CNPJ(message = "CNPJ inválido")
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "CadastroPFDto [id=" + id + "," + "nome=" + nome + "," + "cpf=" + cpf + "," + "cnpj=" + cnpj + ","
				+ "senha=" + senha + "," + "email=" + email + "," + "valorHora=" + valorHora + ","
				+ "qtdHorasTrabalhadasDia=" + qtdHorasTrabalhadasDia + "," + "qtdHorasAlmoco=" + qtdHorasAlmoco + "]";

	}
}
