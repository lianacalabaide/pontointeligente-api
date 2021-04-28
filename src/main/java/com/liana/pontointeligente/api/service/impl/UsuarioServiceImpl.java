package com.liana.pontointeligente.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liana.pontointeligente.api.entities.Usuario;
import com.liana.pontointeligente.api.repository.UsuarioRepository;
import com.liana.pontointeligente.api.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Optional<Usuario> buscaPorEmail(String email) {
		return Optional.ofNullable(usuarioRepository.findByEmail(email));
	}

}
