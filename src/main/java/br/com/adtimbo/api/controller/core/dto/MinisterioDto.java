package br.com.adtimbo.api.controller.core.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.model.core.Ministerio;

public class MinisterioDto {

	private Long id;
	private String titulo;
	private String cor;

	public MinisterioDto(Ministerio ministerio) {
		this.id = ministerio.getId();
		this.titulo = ministerio.getTitulo();
		this.cor = ministerio.getCor();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public static List<MinisterioDto> converter(List<Ministerio> ministerios) {
		return ministerios.stream().map(MinisterioDto::new).collect(Collectors.toList());
	}
}
