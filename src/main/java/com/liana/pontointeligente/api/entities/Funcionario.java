package com.liana.pontointeligente.api.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.liana.pontointeligente.api.enums.PerfilEnum;

@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2396544704781608661L;

	public Funcionario() {
	}
	
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private BigDecimal valorHora;
	private Float qtdHorasTrabalhadasDia;
	private Float qtdHorasAlmoco;
	private PerfilEnum perfil;
	private Date dataCriacao;
	private Date dataAtualizacao;
	private Empresa empresa;
	private List<Lancamento> lancamentos;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="nome", nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "senha", nullable = false)
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Column(name="cpf", nullable = false)
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(name="valor_hora", nullable = true)
	public BigDecimal getValorHora() {
		return valorHora;
	}

	/*
	@Transient
	public Optional<BigDecimal> getValorHoraOpt(){
		return Optional.ofNullable(this.valorHora);
	}*/
	
	public void setValorHora(BigDecimal valorHora) {
		this.valorHora = valorHora;
	}

	@Column(name = "qtd_horas_trabalhadas_dia", nullable = true)
	public Float getQtdHorasTrabalhadasDia() {
		return qtdHorasTrabalhadasDia;
	}

	/*
	@Transient
	public Optional<Float> getQtdHorasTrabalhadasDiaOpt(){
		return Optional.ofNullable(this.qtdHorasTrabalhadasDia);
	} */
	
	public void setQtdHorasTrabalhadasDia(Float qtdHorasTrabalhadasDia) {
		this.qtdHorasTrabalhadasDia = qtdHorasTrabalhadasDia;
	}

	@Column(name = "qtd_horas_almoco", nullable = true)
	public Float getQtdHorasAlmoco() {
		return qtdHorasAlmoco;
	}

	/*
	@Transient
	public Optional<Float> getQtdHorasAlmocoOpt(){
		return Optional.ofNullable(this.qtdHorasAlmoco);
	}*/
	
	public void setQtdHorasAlmoco(Float qtdHorasAlmoco) {
		this.qtdHorasAlmoco = qtdHorasAlmoco;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="perfil", nullable = false)
	public PerfilEnum getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}

	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Column(name = "data_atualizacao", nullable = false)
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	@PreUpdate
	public void preUpdate() {
		this.dataAtualizacao = new Date();
	}

	@PrePersist
	public void prePersist() {
		final Date dataAtual = new Date();
		this.dataCriacao = dataAtual;
		this.dataAtualizacao = dataAtual;
	}

	@Override
	public String toString() {
		return "Funcionario[id" + this.id + ", " +
				"Nome="+this.nome+", " +
				"Email="+this.email+", " +
				"Senha="+this.senha+", " +
				"Cpf="+this.cpf+", " +
				"Valor Hora="+this.valorHora+", " +
				"Qtd Horas Trabalhadas Dia="+this.qtdHorasTrabalhadasDia+", " +
				"Qtd horas almoco="+this.qtdHorasAlmoco+", " +
				"Perfil="+this.perfil+", " +
				"Data criacao="+this.dataCriacao+", " +
				"Data atualizacao="+this.dataAtualizacao+ ", "+
				"Empresa = "+this.empresa.toString() + "]";

	}
}
