package com.fiap.persistencia.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fiap.persistencia.domain.Produto;
import com.fiap.persistencia.repositories.ProdutoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProdutoRespositoryTest {

	@Autowired
	private ProdutoRepository produtoRepository;

	private static final String CODIGO = "09456732";
	private static final String NOME = "Alan";
	private static final Integer QUANTIDADE = 4;
	private static final Double VALOR = 2.45;

	@Before
	public void setUp() throws Exception {
		this.produtoRepository.save(obterDadosProduto());
	}

	@Test
	public void findByNomeTest() {
		Produto produto = this.produtoRepository.findByNome(NOME);

		assertEquals(NOME, produto.getNome());
	}

	@Test
	public void findByCodigoTest() {
		Produto produto = this.produtoRepository.findByCodigo(CODIGO);

		assertEquals(CODIGO, produto.getCodigo());
	}

	private Produto obterDadosProduto() {
		Produto produto = new Produto();
		produto.setCodigo(CODIGO);
		produto.setNome(NOME);
		produto.setQuantidade(QUANTIDADE);
		produto.setValor(VALOR);

		return produto;
	}
}