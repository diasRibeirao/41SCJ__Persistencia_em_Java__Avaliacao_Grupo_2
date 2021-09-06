package com.fiap.persistencia.domain.dto;

import java.io.Serializable;

import com.fiap.persistencia.domain.Cidade;

import lombok.Data;

@Data
public class CidadeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String nome;

	private int estadoId;

	public CidadeDTO(Cidade obj) {
		this.id = obj.getId();
		this.estadoId = obj.getEstado().getId();
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

	public int getEstadoId() {
		return estadoId;
	}

	public void setEstado_id(int estadoId) {
		this.estadoId = estadoId;
	}

}
