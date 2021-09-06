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

import com.fiap.persistencia.domain.Pais;
import com.fiap.persistencia.domain.dto.PaisDTO;
import com.fiap.persistencia.domain.dto.PaisNewDTO;
import com.fiap.persistencia.repositories.PaisRepository;
import com.fiap.persistencia.services.exceptions.DataIntegrityException;
import com.fiap.persistencia.services.exceptions.ObjectNotFoundException;

@Service
public class PaisService {

	@Autowired
	private PaisRepository paisRepository;

	@Cacheable(value = "paisCache", key = "#id")
	public Pais find(Integer id) {
		Optional<Pais> obj = paisRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pais.class.getName()));
	}

	@Cacheable(value = "allPaisCache", unless = "#result.size() == 0")
	public List<Pais> findAll() {
		return paisRepository.findAll();
	}

	@Caching(put = { @CachePut(value = "paisCache", key = "#pais.id") }, evict = {
			@CacheEvict(value = "allPaisCache", allEntries = true) })
	public Pais insert(Pais pais) {
		pais.setId(null);
		return paisRepository.save(pais);
	}

	@Caching(put = { @CachePut(value = "paisCache", key = "#pais.id") }, evict = {
			@CacheEvict(value = "allPaisCache", allEntries = true) })
	public Pais update(Pais pais) {
		Pais newPais = find(pais.getId());
		updateData(newPais, pais);
		return paisRepository.save(newPais);
	}

	@Caching(evict = { @CacheEvict(value = "paisCache", key = "#id"),
			@CacheEvict(value = "allPaisCache", allEntries = true) })
	public void delete(Integer id) {
		find(id);
		try {
			paisRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir");
		}
	}

	public Pais fromDTO(PaisNewDTO paisNewDto) {
		return new Pais(null, paisNewDto.getSigla(), paisNewDto.getNome());
	}

	public Pais fromDTO(PaisDTO paisDto) {
		return new Pais(paisDto.getId(), paisDto.getSigla(), paisDto.getNome());
	}

	private void updateData(Pais newObj, Pais obj) {
		newObj.setSigla(obj.getSigla());
		newObj.setNome(obj.getNome());
	}
}
