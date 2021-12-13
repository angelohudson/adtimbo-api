package br.com.adtimbo.api.controller.core.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.model.core.Endereco;

public class EnderecoDto {

	private Long id;
	private String logradouro;
	private String bairro;
	private String localidade;
	private String uf;
	private String cep;
	private String complemento;
	private String numero;

	public EnderecoDto(Endereco endereco) {
		this.id = endereco.getId();
		this.logradouro = endereco.getLogradouro();
		this.bairro = endereco.getBairro();
		this.localidade = endereco.getLocalidade();
		this.uf = endereco.getUf();
		this.cep = endereco.getCep();
		this.complemento = endereco.getComplemento();
		this.numero = endereco.getNumero();
	}

	public static List<EnderecoDto> converter(List<Endereco> enderecoDto) {
		return enderecoDto.stream().map(EnderecoDto::new).collect(Collectors.toList());
	}

	public Endereco converter() {
		Endereco endereco = new Endereco();
		endereco.setBairro(bairro);
		endereco.setCep(cep);
		endereco.setComplemento(complemento);
		endereco.setLocalidade(localidade);
		endereco.setLogradouro(logradouro);
		endereco.setUf(uf);

		return endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
}
