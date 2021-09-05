package com.fiap.persistencia.domain.dto;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiap.persistencia.domain.Cidade;
import com.fiap.persistencia.domain.Cliente;
import com.fiap.persistencia.domain.Endereco;
import com.fiap.persistencia.domain.Pais;

import lombok.Data;

@Data
public class EderecoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;

	private String cep;

	private Boolean principal;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "cidade_id")
	private Cidade cidade;

	public EderecoDTO(Endereco obj) {
		this.id = obj.getid;
		this.logradouro = obj.getlogradouro;
		this.numero = obj.getnumero;
		this.complemento = obj.getcomplemento;
		this.bairro = obj.getbairro;
		this.cep = obj.getcep;
		this.principal = obj.getprincipal;
		this.cliente = obj.getcliente;
	}
	public EnderecoDTO() {

	}