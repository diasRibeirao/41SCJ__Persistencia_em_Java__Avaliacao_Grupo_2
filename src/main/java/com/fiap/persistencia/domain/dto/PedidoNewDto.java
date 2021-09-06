package com.fiap.persistencia.domain.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PedidoNewDto {

	@NotNull(message = "Preenchimento obrigatório")
	private Integer clienteId;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Set<ItemPedidoDto> itens;

	public PedidoNewDto() {

	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public Set<ItemPedidoDto> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedidoDto> itens) {
		this.itens = itens;
	}

}
