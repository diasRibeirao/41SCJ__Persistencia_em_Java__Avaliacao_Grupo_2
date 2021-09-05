package com.fiap.persistencia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fiap.persistencia.domain.Endereco;
import com.fiap.persistencia.repositories.EnderecoRepository;
import com.fiap.persistencia.services.exceptions.DataIntegrityException;
import com.fiap.persistencia.services.exceptions.ObjectNotFoundException;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Endereco find(Integer id) {
		Optional<Endereco> obj = enderecoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Endereco.class.getName()));
	}

	public List<Endereco> findAll() {
		return enderecoRepository.findAll();
	}

	public Endereco insert(Endereco obj) {
		obj.setId(null);
		return enderecoRepository.save(obj);
	}

	public Endereco update(Endereco obj) {
		Endereco newObj = find(obj.getId());
		updateData(newObj, obj);
		return enderecoRepository.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			enderecoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
		}
	}

	public Endereco fromDTO(EnderecoDTO objDto) {
		return new Endereco(objDto.getId(), objDto.getSigla(), objDto.getNome());
	}

	private void updateData(Endereco newObj, Endereco obj) {
		newObj.setSigla(obj.getSigla());
		newObj.setNome(obj.getNome());
	}

}
