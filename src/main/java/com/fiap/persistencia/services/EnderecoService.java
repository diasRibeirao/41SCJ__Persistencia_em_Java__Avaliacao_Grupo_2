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

	@Cacheable(value= "enderecoCache", key= "#id")
	public Endereco find(Integer id) {
		Optional<Endereco> obj = enderecoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Endereco.class.getName()));
	}

	@Cacheable(value= "allEnderecoCache", unless= "#result.size() == 0")
	public List<Endereco> findAll() {
		return enderecoRepository.findAll();
	}

	@Caching(
		 put= { @CachePut(value= "enderecoCache", key= "#endereco.id") },
		 evict= { @CacheEvict(value= "allEnderecoCache", allEntries= true) } 
	)
	public Endereco insert(Endereco endereco) {
		endereco.setId(null);
		return enderecoRepository.save(endereco);
	}

	@Caching(
		 put= { @CachePut(value= "enderecoCache", key= "#endereco.id") },
		 evict= { @CacheEvict(value= "allEnderecoCache", allEntries= true) }
	)
	public Endereco update(Endereco endereco) {
		Endereco newEndereco = find(endereco.getId());
		updateData(newEndereco, endereco);
		return enderecoRepository.save(newEndereco);
	}

	@Caching(
		evict= { 
			@CacheEvict(value= "enderecoCache", key= "#id"),
			@CacheEvict(value= "allEnderecoCache", allEntries= true)
		}
	)
	public void delete(Integer id) {
		find(id);
		try {
			enderecoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
		}
	}

	public Endereco fromDTO(EnderecoDTO objDto) {
		return new Endereco(objDto.getId(), objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), objDto.getPrincipal(), objDto.getCliente(), objDto.getCidade());
	}

	private void updateData(Endereco newObj, Endereco obj) {		
		newObj.setLogradouro(obj.getLogradouro());
		newObj.setNumero(obj.getNumero());
		newObj.setComplemento(obj.getComplemento());
		newObj.setBairro(obj.getBairro());
		newObj.setCep(obj.getCep());
		newObj.setPrincipal(obj.getPrincipal());
		newObj.setCliente(obj.getCliente());
		newObj.setCidade(obj.getCidade());
	}

}