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

import com.fiap.persistencia.domain.Produto;
import com.fiap.persistencia.domain.dto.ProdutoDTO;
import com.fiap.persistencia.domain.dto.ProdutoNewDTO;
import com.fiap.persistencia.services.ProdutoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Produtos", description = "APIs Produtos", tags = { "Produtos" })
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;

	@ApiOperation(value = "Listar todos os produtos", tags = { "Produtos" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ProdutoDTO>> findAll() {
		List<Produto> list = produtoService.findAll();
		List<ProdutoDTO> listDto = list.stream().map(obj -> new ProdutoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@ApiOperation(value = "Buscar o produto pelo id", tags = { "Produtos" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto produto = produtoService.find(id);
		return ResponseEntity.ok().body(produto);
	}

	@ApiOperation(value = "Inserir um produto", tags = { "Produtos" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ProdutoNewDTO produtoNewDTO) {
		Produto produto = produtoService.fromDTO(produtoNewDTO);
		produto = produtoService.insert(produto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Atualizar um produto", tags = { "Produtos" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ProdutoDTO produtoDTO, @PathVariable Integer id) {
		Produto produto = produtoService.fromDTO(produtoDTO);
		produto.setId(id);
		produto = produtoService.update(produto);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Deletar um produto", tags = { "Produtos" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		produtoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
