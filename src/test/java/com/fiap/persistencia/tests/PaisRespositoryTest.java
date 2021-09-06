package com.fiap.persistencia.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fiap.persistencia.domain.Pais;
import com.fiap.persistencia.repositories.PaisRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaisRespositoryTest {
	
	@Autowired
	private PaisRepository paisRepository;
	
	private static final String NOME = "Brasil";
	private static final String SIGLA = "BR";
	
	@Before
	public void setUp() throws Exception {
		this.paisRepository.save(obterDadosPais());
	}
	
	
	@Test
	public void findByNomeTest() {
		Pais pais = this.paisRepository.findByNome(NOME);
		
		assertEquals(NOME, pais.getNome());
	}
	
	public void findBySiglaTest() {
		Pais pais = this.paisRepository.findBySigla(SIGLA);
		
		assertEquals(SIGLA, pais.getSigla());
	}
	
	private Pais obterDadosPais() {
		Pais pais = new Pais();
		pais.setNome(NOME);
		pais.setSigla(SIGLA);
		
		return pais;
	}
}