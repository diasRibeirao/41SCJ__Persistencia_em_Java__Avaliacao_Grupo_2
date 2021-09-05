package com.fiap.persistencia.domain.dto;

import java.io.Serializable;

import com.fiap.persistencia.domain.Produto;

import lombok.Data;

@Data
public class ProdutoDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProdutoDTO() {
	}
	public ProdutoDTO(Produto obj) {
		this.id = obj.getId();
		this.codigo = obj.getCodigo();
		this.nome = obj.getNome();
		this.quantidade = obj.getQuantidade();
		this.valor = obj.getValor();
	}

	private int id;
	
	private String codigo;
	
	private String nome;

	private int quantidade;
	
	private double valor;
	
	
}
