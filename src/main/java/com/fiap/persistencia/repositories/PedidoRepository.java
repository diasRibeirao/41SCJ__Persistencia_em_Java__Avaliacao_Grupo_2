package com.fiap.persistencia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.persistencia.domain.Cliente;
import com.fiap.persistencia.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	@Transactional(readOnly = true)
	List<Pedido> findByCliente(Cliente cliente);

}
