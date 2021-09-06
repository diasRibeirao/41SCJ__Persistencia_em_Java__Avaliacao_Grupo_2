package com.fiap.persistencia.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fiap.persistencia.domain.Cliente;
import com.fiap.persistencia.domain.Pedido;
import com.fiap.persistencia.repositories.PedidoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PedidoRepositoryTest {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	private Cliente cliente;
	
	@Test
	public void findByClientePaginado() {
		List<Pedido> clientes = this.pedidoRepository.findByCliente(cliente);
		
		assertEquals(2, clientes.size());
	}
}