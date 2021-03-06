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

import com.fiap.persistencia.domain.Cidade;
import com.fiap.persistencia.domain.dto.CidadeDTO;
import com.fiap.persistencia.services.CidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Cidades", description = "APIs Cidades", tags = { "Cidades" })
@RequestMapping(value = "/cidades")
public class CidadeResource {

	@Autowired
	private CidadeService cidadeService;

	@ApiOperation(value = "Listar todos os cidades", tags = { "Cidades"})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findAll() {
		List<Cidade> list = cidadeService.findAll();
		List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@ApiOperation(value = "Buscar o cidade pelo id", tags = { "Cidades"})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cidade> find(@PathVariable Integer id) {
		Cidade obj = cidadeService.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Inserir uma cidade", tags = { "Cidades"})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CidadeDTO objDto) {
		Cidade obj = cidadeService.fromDTO(objDto);
		obj = cidadeService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Atualizar uma cidade", tags = { "Cidades"})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CidadeDTO objDto, @PathVariable Integer id) {
		Cidade obj = cidadeService.fromDTO(objDto);
		obj.setId(id); 
		obj = cidadeService.update(obj);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Deletar um cidade", tags = { "Cidades"})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		cidadeService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
