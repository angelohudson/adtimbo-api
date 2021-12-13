package br.com.adtimbo.api.controller.core.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.model.core.FuncaoTitulo;

public class FuncaoTituloDto {
	private Integer id;
	private String titulo;

	public FuncaoTituloDto(FuncaoTitulo funcao) {
		this.id = funcao.getId();
		this.titulo = funcao.getTitulo();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public static List<FuncaoTituloDto> converter(List<FuncaoTitulo> grupo) {
		return grupo.stream().map(g -> new FuncaoTituloDto(g)).collect(Collectors.toList());
	}

}
