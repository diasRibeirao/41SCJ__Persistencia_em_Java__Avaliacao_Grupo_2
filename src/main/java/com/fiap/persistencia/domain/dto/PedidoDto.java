package com.fiap.persistencia.domain.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fiap.persistencia.domain.ItemPedido;
import com.fiap.persistencia.domain.Pedido;

public class PedidoDto {

	private Integer id;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date data;

	private Integer clienteId;

	private Set<ItemPedidoDto> itens;

	public PedidoDto() {

	}

	public PedidoDto(Pedido obj) {
		this.id = obj.getId();
		this.data = obj.getData();
		this.clienteId = obj.getCliente().getId();
		this.itens = new HashSet<ItemPedidoDto>();
		for (ItemPedido ip : obj.getItens()) {
			this.itens.add(new ItemPedidoDto(ip.getProduto().getId(), ip.getQuantidade(), ip.getValor()));
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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
