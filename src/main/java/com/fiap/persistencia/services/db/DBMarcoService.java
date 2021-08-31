package com.fiap.persistencia.services.db;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.persistencia.domain.Produto;
import com.fiap.persistencia.repositories.ProdutoRepository;

@Service
public class DBMarcoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public void instantiateTestDatabase() throws ParseException {
		Produto p1 = new Produto(null, "C01", "Computador", 150, 2000.00);
		Produto p2 = new Produto(null, "I01", "Impressora", 150, 1000.00);
		Produto p3 = new Produto(null, "M01", "Mouse", 150, 100.00);

		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
	}
}
