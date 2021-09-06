package com.fiap.persistencia.domain.dto;

public class ItemPedidoDto {

	private Integer produtoId;

	private Integer quantidade;

	private Double valor;

	public ItemPedidoDto() {

	}

	public ItemPedidoDto(Integer produtoId, Integer quantidade, Double valor) {
		super();
		this.produtoId = produtoId;
		this.quantidade = quantidade;
		this.valor = valor;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}
