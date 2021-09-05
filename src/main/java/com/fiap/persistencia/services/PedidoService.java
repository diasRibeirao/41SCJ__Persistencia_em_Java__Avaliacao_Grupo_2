package com.fiap.persistencia.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fiap.persistencia.domain.Cliente;
import com.fiap.persistencia.domain.ItemPedido;
import com.fiap.persistencia.domain.Pedido;
import com.fiap.persistencia.repositories.ItemPedidoRepository;
import com.fiap.persistencia.repositories.PedidoRepository;
import com.fiap.persistencia.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ClienteService clienteService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setData(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj = pedidoRepository.save(obj);

		for (ItemPedido ip : obj.getItens()) {
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setValor(ip.getProduto().getValor());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
	
	public Page<Pedido> findPage(Integer idCliente, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clienteService.find(idCliente);
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}
}
