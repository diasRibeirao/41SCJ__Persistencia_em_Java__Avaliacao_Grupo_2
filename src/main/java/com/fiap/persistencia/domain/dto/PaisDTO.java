package com.fiap.persistencia.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fiap.persistencia.domain.Pais;
import com.fiap.persistencia.services.validation.PaisUpdate;

@PaisUpdate
public class PaisDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 2, max = 2, message = "O tamanho deve ser 2 caracteres")
	private String sigla;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
	private String nome;

	public PaisDTO() {

	}

	public PaisDTO(Pais pais) {
		this.id = pais.getId();
		this.sigla = pais.getSigla();
		this.nome = pais.getNome();
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
