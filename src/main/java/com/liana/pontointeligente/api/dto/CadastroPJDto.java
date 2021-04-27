package com.liana.pontointeligente.api.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CadastroPJDto {
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private String razaoSocial;
	private String cnpj;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Nome não pode ser vazio")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 a 200 caracteres")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotEmpty(message = "E-mail não pode ser vazio")
	@Length(min = 3, max = 250, message = "E-mail deve conter entre 3 a 200 caracteres")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "Senha não pode ser vazio")
	@Length(min = 3, max = 200, message = "Senha deve conter entre 3 a 200 caracteres")
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@NotEmpty(message = "Razão social não pode ser vazio")
	@Length(min = 3, max = 200, message = "Razão Social deve conter entre 3 a 200 caracteres")
	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@NotEmpty(message = "CNPJ não pode ser vazio")
	@CNPJ(message = "CNPJ inválido")
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "CadastroPjDto [ id=" + id + "," + "nome=" + nome + "," + "email=" + email + "," + "senha=" + senha + ","
				+ "cpf=" + cpf + "," + "razaoSocial=" + razaoSocial + "," + "cnpj=" + cnpj + "]";
	}
}
