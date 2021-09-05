package com.fiap.persistencia.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.fiap.persistencia.domain.Cliente;
import com.fiap.persistencia.domain.ItemPedido;
import com.fiap.persistencia.domain.Pedido;
import com.fiap.persistencia.domain.Produto;
import com.fiap.persistencia.domain.dto.EnderecoDTO;
import com.fiap.persistencia.repositories.ClienteRepository;
import com.fiap.persistencia.repositories.ProdutoRepository;
import com.fiap.persistencia.resources.exceptions.FieldMessage;

public class PedidoUpdateValidator implements ConstraintValidator<PedidoInsert, Pedido> {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public void initialize(PedidoInsert ann) {
	}

	@Override
	public boolean isValid(Pedido pedido, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		Optional<Cliente> cliente = clienteRepository.findById(pedido.getCliente().getId());
		if (cliente == null) {
			list.add(new FieldMessage("clienteId", "Informar um cliente válido"));
		}

		for (ItemPedido itemPedido : pedido.getItens()) {
			Optional<Produto> produto = produtoRepository.findById(itemPedido.getProduto().getId());
			if (produto != null) {
				list.add(new FieldMessage("produtoID", "Informar um produto válido"));
			}
			
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}
}
