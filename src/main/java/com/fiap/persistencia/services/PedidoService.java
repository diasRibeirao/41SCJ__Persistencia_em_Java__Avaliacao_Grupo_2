package com.fiap.persistencia.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
	
	@Cacheable(value= "pedidoCache", key= "#id")
	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Cacheable(value = "allPedidosCache", unless = "#result.size() == 0")
	public List<Pedido> findAll() {
		return pedidoRepository.findAll();
	}
	
	@Cacheable(value = "allPedidosCache", unless = "#result.size() == 0")
	public List<Pedido> findByCliente(Integer idClienten) {
		Cliente cliente =  clienteService.find(idClienten);
		return pedidoRepository.findByCliente(cliente);
	}
	
	@Caching(
			 put= { @CachePut(value= "pedidoCache", key= "#pedido.id")} 
		)
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
	
	
}
