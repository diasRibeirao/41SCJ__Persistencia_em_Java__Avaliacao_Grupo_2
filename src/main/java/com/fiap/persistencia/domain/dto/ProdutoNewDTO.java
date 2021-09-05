package com.fiap.persistencia.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fiap.persistencia.services.validation.ProdutoInsert;

@ProdutoInsert
public class ProdutoNewDTO {

	@NotEmpty(message = "Preenchimento obrigatório")
	private String codigo;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String nome;

	@NotNull(message = "Preenchimento obrigatório")
	private int quantidade;

	@NotNull(message = "Preenchimento obrigatório")
	private double valor;

	public ProdutoNewDTO() {

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
