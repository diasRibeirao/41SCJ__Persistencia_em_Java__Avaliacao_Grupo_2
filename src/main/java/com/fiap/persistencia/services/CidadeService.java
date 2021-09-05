package com.fiap.persistencia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fiap.persistencia.domain.Cidade;
import com.fiap.persistencia.domain.Cliente;
import com.fiap.persistencia.domain.Endereco;
import com.fiap.persistencia.domain.Estado;
import com.fiap.persistencia.domain.dto.CidadeDTO;
import com.fiap.persistencia.domain.dto.ClienteNewDTO;
import com.fiap.persistencia.domain.enums.TipoCliente;
import com.fiap.persistencia.repositories.CidadeRepository;
import com.fiap.persistencia.repositories.EstadoRepository;
import com.fiap.persistencia.services.exceptions.DataIntegrityException;
import com.fiap.persistencia.services.exceptions.ObjectNotFoundException;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	private EstadoRepository estadoRepository;

	public Cidade find(Integer id) {
		Optional<Cidade> obj = cidadeRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName()));
	}

	public List<Cidade> findAll() {
		return cidadeRepository.findAll();
	}

	public Cidade insert(Cidade obj) {
		obj.setId(null);
		return cidadeRepository.save(obj);
	}

	public Cidade update(Cidade obj) {
		Cidade newObj = find(obj.getId());
		updateData(newObj, obj);
		return cidadeRepository.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			cidadeRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
		}
	}

	public Cidade fromDTO(CidadeDTO objDto) {
		Cidade cidade = new Cidade(objDto.getId(),objDto.getNome());
		Estado estado = findEstadoById(objDto.getEstado_id());

		cidade.setEstado(estado);
		return cidade;
	}

	private void updateData(Cidade newObj, Cidade obj) {
		newObj.setNome(obj.getNome());
	}
	
	
	private Estado findEstadoById(Integer estado_Id) {
		Optional<Estado> obj = estadoRepository.findById(estado_Id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + estado_Id + ", Tipo: " + Estado.class.getName()));
	}
	
}
