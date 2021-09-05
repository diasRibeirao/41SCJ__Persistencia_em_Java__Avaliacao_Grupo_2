package com.fiap.persistencia.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.fiap.persistencia.domain.Cidade;
import com.fiap.persistencia.domain.Cliente;
import com.fiap.persistencia.domain.dto.EnderecoNewDTO;
import com.fiap.persistencia.repositories.CidadeRepository;
import com.fiap.persistencia.repositories.ClienteRepository;
import com.fiap.persistencia.resources.exceptions.FieldMessage;

public class EnderecoInsertValidator implements ConstraintValidator<EnderecoInsert, EnderecoNewDTO> {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Override
	public void initialize(EnderecoInsert ann) {
	}

	@Override
	public boolean isValid(EnderecoNewDTO enderecoNewDTO, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		Optional<Cliente> cliente = clienteRepository.findById(enderecoNewDTO.getClienteId());
		if (cliente == null) {
			list.add(new FieldMessage("clienteId", "Informar um cliente válido"));
		}

		Optional<Cidade> cidade = cidadeRepository.findById(enderecoNewDTO.getCidadeId());
		if (cidade != null) {
			list.add(new FieldMessage("cidadeId", "Informar uma cidade válida"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}
}