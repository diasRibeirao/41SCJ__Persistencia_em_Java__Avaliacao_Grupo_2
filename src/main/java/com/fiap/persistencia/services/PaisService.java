package com.fiap.persistencia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fiap.persistencia.domain.Pais;
import com.fiap.persistencia.domain.dto.PaisDTO;
import com.fiap.persistencia.repositories.PaisRepository;
import com.fiap.persistencia.services.exceptions.DataIntegrityException;
import com.fiap.persistencia.services.exceptions.ObjectNotFoundException;

@Service
public class PaisService {

	@Autowired
	private PaisRepository paisRepository;

	public Pais find(Integer id) {
		Optional<Pais> obj = paisRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pais.class.getName()));
	}

	public List<Pais> findAll() {
		return paisRepository.findAll();
	}

	public Pais insert(Pais obj) {
		obj.setId(null);
		return paisRepository.save(obj);
	}

	public Pais update(Pais obj) {
		Pais newObj = find(obj.getId());
		updateData(newObj, obj);
		return paisRepository.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			paisRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
		}
	}

	public Pais fromDTO(PaisDTO objDto) {
		return new Pais(objDto.getId(), objDto.getSigla(), objDto.getNome());
	}



	private void updateData(Pais newObj, Pais obj) {
		newObj.setSigla(obj.getSigla());
		newObj.setNome(obj.getNome());
	}
}
