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

import com.fiap.persistencia.domain.Cliente;
import com.fiap.persistencia.domain.Pais;
import com.fiap.persistencia.domain.dto.ClienteDTO;
import com.fiap.persistencia.domain.dto.ClienteNewDTO;
import com.fiap.persistencia.domain.dto.PaisDTO;
import com.fiap.persistencia.resources.exceptions.EnderecoService;
import com.fiap.persistencia.services.ClienteService;
import com.fiap.persistencia.services.PaisService;

@RestController
@RequestMapping(value = "/endereco")
public class EnderecoResource {
	@Autowired
	private EnderecoService ederecoService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PaisDTO>> findAll() {
		List<Pais> list = ederecoService.findAll();
		List<PaisDTO> listDto = list.stream().map(obj -> new PaisDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pais> find(@PathVariable Integer id) {
		Pais obj = ederecoService.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody PaisDTO objDto) {
		Pais obj = ederecoService.fromDTO(objDto);
		obj = ederecoService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody PaisDTO objDto, @PathVariable Integer id) {
		Pais obj = ederecoService.fromDTO(objDto);
		obj.setId(id);
		obj = ederecoService.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		ederecoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
