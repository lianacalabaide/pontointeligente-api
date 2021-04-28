package com.liana.pontointeligente.api.service;

import java.util.Optional;

import com.liana.pontointeligente.api.entities.Usuario;

public interface UsuarioService {
	/**
	 * Buscar usuário pelo email
	 * @param email
	 * @return Usuario
	 * */
	
	public Optional<Usuario> buscaPorEmail(String email);
}
