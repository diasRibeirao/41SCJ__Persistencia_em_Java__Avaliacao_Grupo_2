package com.fiap.persistencia.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fiap.persistencia.domain.Pedido;
import com.fiap.persistencia.services.PedidoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Pedidos", description = "APIs Pedidos", tags = { "Pedidos" })
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService pedidoService;

	@ApiOperation(value = "Listar todos os pedidos", tags = { "Pedidos" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Pedido>> findAll() {
		List<Pedido> list = pedidoService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@ApiOperation(value = "Buscar o pedido pelo id", tags = { "Pedidos" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido obj = pedidoService.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Buscar os pedidos do cliente", tags = { "Pedidos" })
	@RequestMapping(value = "/{idCliente}", method = RequestMethod.GET)
	public ResponseEntity<List<Pedido>> findByCliente(@PathVariable Integer idCliente) {
		List<Pedido> list = pedidoService.findByCliente(idCliente);
		return ResponseEntity.ok().body(list);
	}

	@ApiOperation(value = "Inserir um pedido", tags = { "País" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido) {
		pedido = pedidoService.insert(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

}
