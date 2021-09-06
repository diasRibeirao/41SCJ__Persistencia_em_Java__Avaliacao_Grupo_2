package com.fiap.persistencia.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fiap.persistencia.domain.Cliente;
import com.fiap.persistencia.domain.ItemPedido;
import com.fiap.persistencia.domain.Pedido;
import com.fiap.persistencia.domain.dto.ItemPedidoDto;
import com.fiap.persistencia.domain.dto.PedidoNewDto;
import com.fiap.persistencia.repositories.ItemPedidoRepository;
import com.fiap.persistencia.repositories.PedidoRepository;
import com.fiap.persistencia.services.exceptions.DataIntegrityException;
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

	@Cacheable(value = "pedidoCache", key = "#id")
	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Cacheable(value = "allPedidosCache", unless = "#result.size() == 0")
	public List<Pedido> findAll() {
		return pedidoRepository.findAll();
	}

	@Cacheable(value = "allPedidosClienteCache", key = "#idCliente", unless = "#result.size() == 0")
	public List<Pedido> findByCliente(Integer idCliente) {
		Cliente cliente = clienteService.find(idCliente);
		return pedidoRepository.findByCliente(cliente);
	}

	@Caching(put = { @CachePut(value = "pedidoCache", key = "#pedido.id") }, evict = {
			@CacheEvict(value = "allPedidosCache", allEntries = true) })
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setData(new Date());
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
		pedido = pedidoRepository.save(pedido);

		for (ItemPedido ip : pedido.getItens()) {
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setValor(ip.getProduto().getValor());
			ip.setPedido(pedido);
		}

		itemPedidoRepository.saveAll(pedido.getItens());
		return pedido;
	}
	
	@Caching(evict = { @CacheEvict(value = "pedidoCache", key = "#id"),
			@CacheEvict(value = "allPedidosCache", allEntries = true) })
	public void delete(Integer id) {
		find(id);
		try {
			pedidoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir");
		}
	}

	public Pedido fromDTO(@Valid PedidoNewDto pedidoNewDto) {
		Pedido pedido = new Pedido(null, new Date(), clienteService.find(pedidoNewDto.getClienteId()));
		pedido.setItens(new HashSet<ItemPedido>());

		for (ItemPedidoDto ipDto : pedidoNewDto.getItens()) {
			pedido.getItens().add(new ItemPedido(pedido, produtoService.find(ipDto.getProdutoId()),
					ipDto.getQuantidade(), ipDto.getValor()));
		}

		return pedido;
	}

}
