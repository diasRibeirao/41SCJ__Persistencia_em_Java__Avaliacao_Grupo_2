package com.fiap.persistencia.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.fiap.persistencia.domain.Produto;
import com.fiap.persistencia.domain.dto.ProdutoDTO;
import com.fiap.persistencia.repositories.ProdutoRepository;
import com.fiap.persistencia.resources.exceptions.FieldMessage;

public class ProdutoUpdateValidator implements ConstraintValidator<ProdutoUpdate, ProdutoDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public void initialize(ProdutoUpdate ann) {
	}

	@Override
	public boolean isValid(ProdutoDTO produtoDTO, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));

		List<FieldMessage> list = new ArrayList<>();

		Produto aux = produtoRepository.findByCodigo(produtoDTO.getCodigo());
		if (aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("codigo", "Código já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}
}
