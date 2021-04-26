package com.liana.pontointeligente.api.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.liana.pontointeligente.api.entities.Empresa;
import com.liana.pontointeligente.api.entities.Funcionario;
import com.liana.pontointeligente.api.enums.PerfilEnum;
import com.liana.pontointeligente.api.utils.PasswordUtils;

@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String EMAIL="lianusca@gmail.com";
	private static final String CPF ="04705336935";
	
	@BeforeEach
	public void setUp() {
		Empresa empresa = empresaRepository.save(obterDadosEmpresa());
		funcionarioRepository.save(obterDadosFuncionario(empresa));
	}
	
	@AfterEach
	public void tearDown() {
		funcionarioRepository.deleteAll();
		empresaRepository.deleteAll();
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("514635000100");
		empresa.setRazaoSocial("Teste");
		return empresa;
	}
	
	private Funcionario obterDadosFuncionario(Empresa empresa) {
		Funcionario funcionario = new Funcionario();
		funcionario.setEmail(EMAIL);
		funcionario.setCpf(CPF);
		funcionario.setNome("Liana");
		funcionario.setEmpresa(empresa);
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt("1234"));
		return funcionario;
	}
	
	@Test
	public void testFuncionarioPorEmail() {
		Funcionario byEmail = funcionarioRepository.findByEmail(EMAIL);
		Assertions.assertEquals(EMAIL, byEmail.getEmail());
	}
	
	@Test
	public void testPorCpf() {
		Funcionario byCpf = funcionarioRepository.findByCpf(CPF);
		Assertions.assertEquals(CPF, byCpf.getCpf());
	}
	
	@Test
	public void testFuncionarioPorEmailOuCpf() {
		Funcionario byCpfOrEmail = funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
		Assertions.assertNotNull(byCpfOrEmail);
	}

	@Test
	public void testFuncionarioPorEmailInvalidoOuCpf() {
		Funcionario byCpfOrEmail = funcionarioRepository.findByCpfOrEmail(CPF, "email@email.com");
		Assertions.assertNotNull(byCpfOrEmail);
	}	
	
	@Test
	public void testFuncionarioPorEmailInvalidoECpfInvalido() {
		Funcionario findByCpfOrEmail = funcionarioRepository.findByCpfOrEmail("03267982915", "email@email.com");
		Assertions.assertNull(findByCpfOrEmail);
	}
}
