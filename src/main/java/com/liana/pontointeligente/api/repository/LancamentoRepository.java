package com.liana.pontointeligente.api.repository;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.liana.pontointeligente.api.entities.Lancamento;

@NamedQueries({
	@NamedQuery(query = "SELECT lanc FROM Lancamento lanc where lanc.funcionario.id = :funcionarioId", name = "LancamentoRepository.findByFuncionarioId")
})
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
	List<Lancamento> findByFuncionario(@Param("funcionarioId") Long funcionarioId);
	
	Page<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable page);
}
