package com.fiap.persistencia.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fiap.persistencia.domain.Produto;
import com.fiap.persistencia.services.validation.ProdutoUpdate;

@ProdutoUpdate
public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String codigo;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String nome;

	@NotNull(message = "Preenchimento obrigatório")
	private int quantidade;

	@NotNull(message = "Preenchimento obrigatório")
	private double valor;

	public ProdutoDTO() {
	}

	public ProdutoDTO(Produto obj) {
		this.id = obj.getId();
		this.codigo = obj.getCodigo();
		this.nome = obj.getNome();
		this.quantidade = obj.getQuantidade();
		this.valor = obj.getValor();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
