package com.fiap.persistencia.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
import com.fiap.persistencia.domain.dto.PedidoDto;
import com.fiap.persistencia.domain.dto.PedidoNewDto;
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
	public ResponseEntity<List<PedidoDto>> findAll() {
		List<Pedido> list = pedidoService.findAll();
		List<PedidoDto> listDto = list.stream().map(pedido -> new PedidoDto(pedido)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@ApiOperation(value = "Buscar o pedido pelo id", tags = { "Pedidos" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido obj = pedidoService.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Inserir um pedido", tags = { "Pedidos" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody PedidoNewDto pedidoNewDto) {
		Pedido pedido = pedidoService.fromDTO(pedidoNewDto);
		pedido = pedidoService.insert(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Deletar um pedido", tags = { "Pedidos" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		pedidoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
