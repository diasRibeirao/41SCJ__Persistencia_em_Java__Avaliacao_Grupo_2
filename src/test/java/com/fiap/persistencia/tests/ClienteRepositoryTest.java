package com.fiap.persistencia.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fiap.persistencia.domain.Cliente;
import com.fiap.persistencia.repositories.ClienteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClienteRepositoryTest {
	
	@Autowired
	private ClienteRepository clienteRespository;
	
	
	/*Dados do Cliente*/
	private static final String CPF_OR_CNPJ = "12345678990";
	private static final String NOME = "Alan";
	private static final String EMAIL = "email@email.com";
	private static final Integer TIPO = 1;
		
	
	@Before
	public void setUp() throws Exception {
		this.clienteRespository.save(obterDadosCliente());
	}
	
	
	@Test
	public void findByEmailTest() {
		Cliente cliente = this.clienteRespository.findByEmail(EMAIL);
		
		assertEquals(EMAIL, cliente.getEmail());
	}
	
	
	@Test
	public void findByEmailOrCpfOrCnpjParaEmailInvalido() {
		Cliente cliente = this.clienteRespository.findByEmail(EMAIL);
		
		assertEquals(CPF_OR_CNPJ, cliente.getCpfOuCnpj());
	}
	
	private Cliente obterDadosCliente() {
		Cliente cliente = new Cliente();
		cliente.setCpfOuCnpj(CPF_OR_CNPJ);
		cliente.setNome(NOME);
		cliente.setEmail(EMAIL);
		cliente.setTipo(TIPO);

		return cliente;
	}
}
