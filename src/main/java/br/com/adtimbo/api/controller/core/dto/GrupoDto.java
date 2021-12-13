package br.com.adtimbo.api.controller.core.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.model.core.Grupo;

public class GrupoDto {

	private Long id;
	private String titulo;
	private MinisterioDto ministerioDto;

	public GrupoDto(Grupo grupo) {
		this.id = grupo.getId();
		this.titulo = grupo.getTitulo();
		this.ministerioDto = new MinisterioDto(grupo.getMinisterio());
	}

	public static List<GrupoDto> converter(List<Grupo> grupos) {
		return grupos.stream().map(GrupoDto::new).collect(Collectors.toList());
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

	public MinisterioDto getMinisterioDto() {
		return ministerioDto;
	}

	public void setMinisterioDto(MinisterioDto ministerioDto) {
		this.ministerioDto = ministerioDto;
	}

}
