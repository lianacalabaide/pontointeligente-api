package com.liana.pontointeligente.api.repository;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.liana.pontointeligente.api.entities.Empresa;
import com.liana.pontointeligente.api.entities.Funcionario;
import com.liana.pontointeligente.api.entities.Lancamento;
import com.liana.pontointeligente.api.enums.PerfilEnum;
import com.liana.pontointeligente.api.enums.TipoEnum;
import com.liana.pontointeligente.api.utils.PasswordUtils;

@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	private Long funcionarioId;
	
	@BeforeEach
	public void setUp() {
		Empresa empresa = empresaRepository.save(obterDadosEmpresa());
		Funcionario funcionario = funcionarioRepository.save(obterDadosFuncionario(empresa));
		this.funcionarioId = funcionario.getId();
		lancamentoRepository.save(obterDadosLancamento(funcionario));
		lancamentoRepository.save(obterDadosLancamento(funcionario));
	}
	
	private Lancamento obterDadosLancamento(Funcionario funcionario) {
		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date());
		lancamento.setDescricao("Teste Liana");
		lancamento.setFuncionario(funcionario);
		lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
		return lancamento;
	}

	@AfterEach
	public void tearDown() {
		this.empresaRepository.deleteAll();
	}
	
	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("514635000100");
		empresa.setRazaoSocial("Teste");
		return empresa;
	}
	
	private Funcionario obterDadosFuncionario(Empresa empresa) {
		Funcionario funcionario = new Funcionario();
		funcionario.setEmail("lianuscaa@gmail.com");
		funcionario.setCpf("04705336825");
		funcionario.setNome("Liana");
		funcionario.setEmpresa(empresa);
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt("1234"));
		return funcionario;
	}
	
	@Test
	public void testBuscaFuncionarioPorIdPaginado() {
		PageRequest page = PageRequest.of(0, 10);
		org.springframework.data.domain.Page<Lancamento> pageable = lancamentoRepository.findByFuncionarioId(this.funcionarioId, page);
		Assertions.assertEquals(10, pageable.getSize());
	}
}
