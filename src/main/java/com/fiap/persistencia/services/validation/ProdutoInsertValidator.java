package com.fiap.persistencia.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.fiap.persistencia.domain.Produto;
import com.fiap.persistencia.domain.dto.ProdutoNewDTO;
import com.fiap.persistencia.repositories.ProdutoRepository;
import com.fiap.persistencia.resources.exceptions.FieldMessage;

public class ProdutoInsertValidator implements ConstraintValidator<ProdutoInsert, ProdutoNewDTO> {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public void initialize(ProdutoInsert ann) {
	}

	@Override
	public boolean isValid(ProdutoNewDTO produtoNewDTO, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		Produto aux = produtoRepository.findByCodigo(produtoNewDTO.getCodigo());
		if (aux != null) {
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