package com.fiap.persistencia.domain.dto;

import java.io.Serializable;

import com.fiap.persistencia.domain.Pais;

import lombok.Data;

@Data
public class PaisDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String sigla;

	private String nome;

	public PaisDTO(Pais obj) {
		this.id = obj.getId();
		this.sigla = obj.getSigla();
		this.nome = obj.getNome();
	}

	public PaisDTO() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
