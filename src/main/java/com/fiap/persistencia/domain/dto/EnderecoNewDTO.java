package com.fiap.persistencia.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fiap.persistencia.services.validation.EnderecoInsert;

@EnderecoInsert
public class EnderecoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
	private String logradouro;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String numero;

	private String complemento;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String bairro;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String cep;

	@NotNull(message = "Preenchimento obrigatório")
	private Boolean principal;

	@NotNull(message = "Preenchimento obrigatório")
	private Integer clienteId;

	@NotNull(message = "Preenchimento obrigatório")
	private Integer cidadeId;

	public EnderecoNewDTO() {

	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Boolean getPrincipal() {
		return principal;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}

	

}
