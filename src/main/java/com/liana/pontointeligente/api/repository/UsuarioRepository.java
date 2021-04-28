package com.liana.pontointeligente.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.liana.pontointeligente.api.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	@Transactional(readOnly = true)
	Usuario findByEmail(String email);
}
