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

import com.fiap.persistencia.domain.Cidade;
import com.fiap.persistencia.domain.Cliente;
import com.fiap.persistencia.domain.Endereco;
import com.fiap.persistencia.domain.dto.ClienteDTO;
import com.fiap.persistencia.domain.dto.ClienteNewDTO;
import com.fiap.persistencia.domain.enums.TipoCliente;
import com.fiap.persistencia.repositories.ClienteRepository;
import com.fiap.persistencia.services.exceptions.DataIntegrityException;
import com.fiap.persistencia.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeService cidadeService;

	@Cacheable(value= "clienteCache", key= "#id")
	public Cliente find(Integer id) {
		Optional<Cliente> clientes = clienteRepository.findById(id);
		return clientes.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Cacheable(value= "allClientesCache", unless= "#result.size() == 0")
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	@Caching(
		 put= { @CachePut(value= "clienteCache", key= "#cliente.id") },
		 evict= { @CacheEvict(value= "allClientesCache", allEntries= true) } 
	)
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		return clienteRepository.save(cliente);
	}

	@Caching(
		 put= { @CachePut(value= "clienteCache", key= "#cliente.id") },
		 evict= { @CacheEvict(value= "allClientesCache", allEntries= true) }
	)
	public Cliente update(Cliente cliente) {
		Cliente newObj = find(cliente.getId());
		updateData(newObj, cliente);
		return clienteRepository.save(newObj);
	}

	@Caching(
		evict= { 
			@CacheEvict(value= "clienteCache", key= "#id"),
			@CacheEvict(value= "allClientesCache", allEntries= true)
		}
	)
	public void delete(Integer id) {
		find(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
		}
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), null, objDto.getNome(), objDto.getEmail(), null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getCpfOuCnpj(), objDto.getNome(), objDto.getEmail(),
				TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = cidadeService.find(objDto.getCidadeId());
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), objDto.getPrincipal(), cli, cid);
		cli.getEnderecos().add(end);
		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
