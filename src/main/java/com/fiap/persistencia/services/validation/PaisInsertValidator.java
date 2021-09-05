package com.fiap.persistencia.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.fiap.persistencia.domain.Pais;
import com.fiap.persistencia.domain.dto.PaisNewDTO;
import com.fiap.persistencia.repositories.PaisRepository;
import com.fiap.persistencia.resources.exceptions.FieldMessage;

public class PaisInsertValidator implements ConstraintValidator<PaisInsert, PaisNewDTO> {

	@Autowired
	private PaisRepository paisRepository;

	@Override
	public void initialize(PaisInsert ann) {
	}

	@Override
	public boolean isValid(PaisNewDTO paisNewDTO, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		Pais aux = paisRepository.findByNome(paisNewDTO.getNome());
		if (aux != null) {
			list.add(new FieldMessage("nome", "Nome já existente"));
		}

		aux = paisRepository.findBySigla(paisNewDTO.getSigla());
		if (aux != null) {
			list.add(new FieldMessage("sigla", "Sigla já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}
}