package com.fiap.persistencia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fiap.persistencia.domain.Endereco;
import com.fiap.persistencia.domain.dto.EnderecoDTO;
import com.fiap.persistencia.repositories.EnderecoRepository;
import com.fiap.persistencia.services.exceptions.DataIntegrityException;
import com.fiap.persistencia.services.exceptions.ObjectNotFoundException;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ClienteService clienteService;

	@Cacheable(value = "enderecoCache", key = "#id")
	public Endereco find(Integer id) {
		Optional<Endereco> obj = enderecoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Endereco.class.getName()));
	}

	@Cacheable(value = "allEnderecoCache", unless = "#result.size() == 0")
	public List<Endereco> findAll() {
		return enderecoRepository.findAll();
	}

	@Caching(put = { @CachePut(value = "enderecoCache", key = "#endereco.id") }, evict = {
			@CacheEvict(value = "allEnderecoCache", allEntries = true) })
	public Endereco insert(Endereco endereco) {
		endereco.setId(null);
		return enderecoRepository.save(endereco);
	}

	@Caching(put = { @CachePut(value = "enderecoCache", key = "#endereco.id") }, evict = {
			@CacheEvict(value = "allEnderecoCache", allEntries = true) })
	public Endereco update(Endereco endereco) {
		Endereco newEndereco = find(endereco.getId());
		updateData(newEndereco, endereco);
		return enderecoRepository.save(newEndereco);
	}

	@Caching(evict = { @CacheEvict(value = "enderecoCache", key = "#id"),
			@CacheEvict(value = "allEnderecoCache", allEntries = true) })
	public void delete(Integer id) {
		find(id);
		try {
			enderecoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
		}
	}

	public Endereco fromDTO(EnderecoDTO enderecoDTO) {
		return new Endereco(enderecoDTO.getId(), enderecoDTO.getLogradouro(), enderecoDTO.getNumero(),
				enderecoDTO.getComplemento(), enderecoDTO.getBairro(), enderecoDTO.getCep(), enderecoDTO.getPrincipal(),
				clienteService.find(enderecoDTO.getCliente().getId()), enderecoDTO.getCidade());
	}

	private void updateData(Endereco newEndereco, Endereco endereco) {
		newEndereco.setLogradouro(endereco.getLogradouro());
		newEndereco.setNumero(endereco.getNumero());
		newEndereco.setComplemento(endereco.getComplemento());
		newEndereco.setBairro(endereco.getBairro());
		newEndereco.setCep(endereco.getCep());
		newEndereco.setPrincipal(endereco.getPrincipal());
		newEndereco.setCliente(endereco.getCliente());
		newEndereco.setCidade(endereco.getCidade());
	}

}