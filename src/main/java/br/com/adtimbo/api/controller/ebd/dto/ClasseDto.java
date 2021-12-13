package br.com.adtimbo.api.controller.ebd.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.model.ebd.core.Classe;

public class ClasseDto {
	public Integer id;
	public Integer dataFinal;
	public Integer dataInicial;
	public String titulo;

	public ClasseDto(Classe classe) {
		this.id = classe.getId();
		this.dataFinal = classe.getDataFinal();
		this.dataInicial = classe.getDataInicial();
		this.titulo = classe.getTitulo();
	}

	public static List<ClasseDto> converter(List<Classe> classesDto) {
		return classesDto.stream().map(ClasseDto::new).collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Integer dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Integer getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Integer dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
