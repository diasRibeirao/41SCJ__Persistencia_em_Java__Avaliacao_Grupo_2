package com.fiap.persistencia.domain.dto;

import java.io.Serializable;

import com.fiap.persistencia.domain.Cidade;

import lombok.Data;

@Data
public class CidadeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String nome;

	private int estado_id;

	public CidadeDTO(Cidade obj) {
		this.id = obj.getId();
		this.estado_id = obj.getEstado().getId();
		this.nome = obj.getNome();
	}
	public CidadeDTO() {

	}



}
