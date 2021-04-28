package com.liana.pontointeligente.api.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.liana.pontointeligente.api.entities.Empresa;
import com.liana.pontointeligente.api.service.EmpresaService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmpresaControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private EmpresaService empresaService;

	private static final String BUSCAR_EMPRESA_URL_CNPJ = "/api/empresas/cnpj";
	private static final Long ID = 1L;
	private static final String CPNJ = "00636618000142";
	private static final String RAZAO_SOCIAL = "Liana Calabaide Corporation";

	@Test
	public void testBuscarEmpresaCnpjInvalido() throws Exception {
		when(empresaService.buscaPorCnpj(CPNJ)).thenReturn(Optional.empty());
		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_URL_CNPJ + CPNJ)
				.accept(org.springframework.http.MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors").value("Empresa não encontrada para o CNPJ " + CPNJ));
	}

	@Test
	public void testBuscarEmpresaCnpj() throws Exception {
		when(empresaService.buscaPorCnpj(CPNJ)).thenReturn(Optional.of(this.retornarEmpresa()));
		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_URL_CNPJ + CPNJ)
				.accept(org.springframework.http.MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.razaoSocial",equalTo(RAZAO_SOCIAL)))
				.andExpect(jsonPath("$.errors").isEmpty());

	}

	private Empresa retornarEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial(RAZAO_SOCIAL);
		empresa.setCnpj(CPNJ);
		empresa.setId(ID);
		return empresa;
	}
}
