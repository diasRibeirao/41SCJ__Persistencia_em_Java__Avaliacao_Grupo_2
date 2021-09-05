
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

import com.fiap.persistencia.domain.Endereco;
import com.fiap.persistencia.domain.dto.EnderecoDTO;
import com.fiap.persistencia.domain.dto.EnderecoNewDTO;
import com.fiap.persistencia.services.EnderecoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Endereços", description = "APIs Endereços", tags = { "Enderecos" })
@RequestMapping(value = "/enderecos")
public class EnderecoResource {
	@Autowired
	private EnderecoService enderecoService;

	@ApiOperation(value = "Listar todos os endereços", tags = { "Enderecos"})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EnderecoDTO>> findAll() {
		List<Endereco> list = enderecoService.findAll();
		List<EnderecoDTO> listDto = list.stream().map(obj -> new EnderecoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@ApiOperation(value = "Buscar o endereço pelo id", tags = { "Enderecos"})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Endereco> find(@PathVariable Integer id) {
		Endereco endereco = enderecoService.find(id);
		return ResponseEntity.ok().body(endereco);
	}

	@ApiOperation(value = "Inserir um endereço", tags = { "Enderecos"})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody EnderecoNewDTO enderecoNewDTO) {
		Endereco endereco = enderecoService.fromDTO(enderecoNewDTO);
		endereco = enderecoService.insert(endereco);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(endereco.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Atualizar um endereço", tags = { "Enderecos"})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody EnderecoDTO enderecoDTO, @PathVariable Integer id) {
		Endereco endereco = enderecoService.fromDTO(enderecoDTO);
		endereco.setId(id);
		endereco = enderecoService.update(endereco);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Deletar um endereço", tags = { "Enderecos"})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		enderecoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}