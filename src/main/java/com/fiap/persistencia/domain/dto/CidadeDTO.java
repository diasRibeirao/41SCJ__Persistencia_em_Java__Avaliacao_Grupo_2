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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getEstado_id() {
		return estado_id;
	}

	public void setEstado_id(int estado_id) {
		this.estado_id = estado_id;
	}

}
