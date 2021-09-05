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
import com.fiap.persistencia.repositories.CidadeRepository;
import com.fiap.persistencia.repositories.ClienteRepository;
import com.fiap.persistencia.services.exceptions.DataIntegrityException;
import com.fiap.persistencia.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Cacheable(value= "clienteCache", key= "#id")
	public Cliente find(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
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
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return clienteRepository.save(obj);
	}

	@Caching(
		 put= { @CachePut(value= "clienteCache", key= "#cliente.id") },
		 evict= { @CacheEvict(value= "allClientesCache", allEntries= true) }
	)
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return clienteRepository.save(newObj);
	}

	@Caching(
		 put= { @CachePut(value= "clienteCache", key= "#cliente.id") },
		 evict= { @CacheEvict(value= "allClientesCache", allEntries= true) }
	)
	public void delete(Integer id) {
		find(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
		}
	}

	private Cidade findCidadeById(Integer cidade_Id) {
		Optional<Cidade> obj = cidadeRepository.findById(cidade_Id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + cidade_Id + ", Tipo: " + Cidade.class.getName()));
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), null, objDto.getNome(), objDto.getEmail(), null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getCpfOuCnpj(), objDto.getNome(), objDto.getEmail(),
				TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = findCidadeById(objDto.getCidadeId());
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
