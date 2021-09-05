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

import com.fiap.persistencia.domain.Produto;
import com.fiap.persistencia.domain.dto.ProdutoDTO;
import com.fiap.persistencia.repositories.ProdutoRepository;
import com.fiap.persistencia.services.exceptions.DataIntegrityException;
import com.fiap.persistencia.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Cacheable(value= "produtoCache", key= "#id")
	public Produto find(Integer id) {
		Optional<Produto> obj = produtoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	@Cacheable(value= "allClientesCache", unless= "#result.size() == 0")
	public List<Produto> findAll() {
		return produtoRepository.findAll();
	}
	@Caching(
			 put= { @CachePut(value= "produtoCache", key= "#produto.id") },
			 evict= { @CacheEvict(value= "allClientesCache", allEntries= true) } 
	)
	public Produto insert(Produto obj) {
		obj.setId(null);
		return produtoRepository.save(obj);
	}
	@Caching(
			 put= { @CachePut(value= "produtoCache", key= "#produto.id") },
			 evict= { @CacheEvict(value= "allClientesCache", allEntries= true) }
		)
	public Produto update(Produto obj) {
		Produto newObj = find(obj.getId());
		updateData(newObj, obj);
		return produtoRepository.save(newObj);
	}
	@Caching(
			evict= { 
				@CacheEvict(value= "produtoCache", key= "#id"),
				@CacheEvict(value= "allClientesCache", allEntries= true)
			}
		)
	public void delete(Integer id) {
		find(id);
		try {
			produtoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
		}
	}

	public Produto fromDTO(ProdutoDTO objDto) {
		return new Produto(objDto.getId(), objDto.getCodigo(), objDto.getNome(), objDto.getQuantidade(), objDto.getValor());
	}



	private void updateData(Produto newObj, Produto obj) {
		newObj.setValor(obj.getValor());
		newObj.setQuantidade(obj.getQuantidade());
		newObj.setNome(obj.getNome());
	}
}
