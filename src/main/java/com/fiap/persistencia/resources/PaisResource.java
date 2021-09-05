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

import com.fiap.persistencia.domain.Pais;
import com.fiap.persistencia.domain.dto.PaisDTO;
import com.fiap.persistencia.domain.dto.PaisNewDTO;
import com.fiap.persistencia.services.PaisService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "País", description = "APIs País", tags = { "País" })
@RequestMapping(value = "/pais")
public class PaisResource {

	@Autowired
	private PaisService paisService;

	@ApiOperation(value = "Listar todos os países", tags = { "País" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PaisDTO>> findAll() {
		List<Pais> list = paisService.findAll();
		List<PaisDTO> listDto = list.stream().map(obj -> new PaisDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@ApiOperation(value = "Buscar o país pelo id", tags = { "País" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pais> find(@PathVariable Integer id) {
		Pais pais = paisService.find(id);
		return ResponseEntity.ok().body(pais);
	}

	@ApiOperation(value = "Inserir um país", tags = { "País" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody PaisNewDTO paisDTO) {
		Pais pais = paisService.fromDTO(paisDTO);
		pais = paisService.insert(pais);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pais.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Atualizar um país", tags = { "País" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody PaisDTO paisDTO, @PathVariable Integer id) {
		Pais pais = paisService.fromDTO(paisDTO);
		pais.setId(id);
		pais = paisService.update(pais);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Deletar um país", tags = { "País" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		paisService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
