package com.fiap.persistencia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.persistencia.domain.Pais;
import com.fiap.persistencia.domain.Produto;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {
	@Transactional(readOnly=true) 
	Produto findByNome(String nome);
	
	@Transactional(readOnly=true)
	Produto findBySigla(String sigla);
}
