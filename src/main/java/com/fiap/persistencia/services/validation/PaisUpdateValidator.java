package com.fiap.persistencia.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.fiap.persistencia.domain.Pais;
import com.fiap.persistencia.domain.dto.PaisDTO;
import com.fiap.persistencia.repositories.PaisRepository;
import com.fiap.persistencia.resources.exceptions.FieldMessage;

public class PaisUpdateValidator implements ConstraintValidator<PaisUpdate, PaisDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private PaisRepository paisRepository;

	@Override
	public void initialize(PaisUpdate ann) {
	}

	@Override
	public boolean isValid(PaisDTO paisDTO, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));

		List<FieldMessage> list = new ArrayList<>();

		Pais aux = paisRepository.findByNome(paisDTO.getNome());
		if (aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("nome", "Nome já existente"));
		}

		aux = paisRepository.findBySigla(paisDTO.getSigla());
		if (aux != null && !aux.getId().equals(uriId)) {
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
