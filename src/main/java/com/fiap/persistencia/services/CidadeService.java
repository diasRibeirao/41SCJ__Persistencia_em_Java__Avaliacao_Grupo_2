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
import com.fiap.persistencia.domain.Estado;
import com.fiap.persistencia.domain.dto.CidadeDTO;
import com.fiap.persistencia.repositories.CidadeRepository;
import com.fiap.persistencia.repositories.EstadoRepository;
import com.fiap.persistencia.services.exceptions.DataIntegrityException;
import com.fiap.persistencia.services.exceptions.ObjectNotFoundException;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Cacheable(value = "cidadeCache", key = "#id")
	public Cidade find(Integer id) {
		Optional<Cidade> obj = cidadeRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName()));
	}

	@Cacheable(value = "allCidadesCache", unless = "#result.size() == 0")
	public List<Cidade> findAll() {
		return cidadeRepository.findAll();
	}

	@Caching(put = { @CachePut(value = "cidadeCache", key = "#cidade.id") }, evict = {
			@CacheEvict(value = "allCidadesCache", allEntries = true) })
	public Cidade insert(Cidade cidade) {
		cidade.setId(null);
		return cidadeRepository.save(cidade);
	}

	@Caching(put = { @CachePut(value = "cidadeCache", key = "#cidade.id") }, evict = {
			@CacheEvict(value = "allCidadesCache", allEntries = true) })
	public Cidade update(Cidade cidade) {
		Cidade newCidade = find(cidade.getId());
		updateData(newCidade, cidade);
		return cidadeRepository.save(newCidade);
	}

	@Caching(evict = { @CacheEvict(value = "cidadeCache", key = "#id"),
			@CacheEvict(value = "allCidadesCache", allEntries = true) })
	public void delete(Integer id) {
		find(id);
		try {
			cidadeRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir");
		}
	}

	public Cidade fromDTO(CidadeDTO cidadeDTO) {
		Cidade cidade = new Cidade(cidadeDTO.getId(), cidadeDTO.getNome());
		Estado estado = findEstadoById(cidadeDTO.getEstadoId());

		cidade.setEstado(estado);
		return cidade;
	}

	private void updateData(Cidade newCidade, Cidade cidade) {
		newCidade.setNome(cidade.getNome());
		newCidade.setEstado(cidade.getEstado());
	}

	private Estado findEstadoById(Integer estado_Id) {
		Optional<Estado> obj = estadoRepository.findById(estado_Id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + estado_Id + ", Tipo: " + Estado.class.getName()));
	}

}
