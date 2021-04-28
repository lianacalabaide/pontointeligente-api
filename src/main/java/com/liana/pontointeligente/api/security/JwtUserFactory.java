package com.liana.pontointeligente.api.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.liana.pontointeligente.api.entities.Funcionario;
import com.liana.pontointeligente.api.enums.PerfilEnum;

public class JwtUserFactory {
	public JwtUserFactory() {
	}

	/**
	 * Converter e gerar um JwtUser com base nos dados do funcionário
	 * 
	 * @param funcionario return JwtUser
	 */
	public static JwtUser create(Funcionario usuario) {
		return new JwtUser(usuario.getId(), usuario.getEmail(), usuario.getSenha(),
				mapToGrantedAuthority(usuario.getPerfil()));
	}

	/**
	 * Converter o perfil do usuário para o formato utilizado pelo Sprint Security
	 * 
	 * @param perfilEnum
	 * @return List<GrantedAuthority>
	 */
	private static List<GrantedAuthority> mapToGrantedAuthority(PerfilEnum perfilEnum) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
		return authorities;
	}
}
