package com.liana.pontointeligente.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.liana.pontointeligente.api.entities.Funcionario;
import com.liana.pontointeligente.api.security.JwtUserFactory;
import com.liana.pontointeligente.api.service.FuncionarioService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private FuncionarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Funcionario> funcionario = usuarioService.buscarFuncionarioPorEmail(username);
		if (funcionario.isPresent()) {
			return JwtUserFactory.create(funcionario.get());
		}
		throw new UsernameNotFoundException("E-mail não encontrado");
	}

}
