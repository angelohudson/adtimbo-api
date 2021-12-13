package br.com.adtimbo.api.controller.core.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.model.core.Funcao;

public class FuncaoDto {
	private Integer id;
	private String tipo;
	private MembroDto membro;

	public FuncaoDto(Funcao funcao) {
		this.id = funcao.getId();
		this.tipo = funcao.getTipo();
		this.membro = new MembroDto(funcao.getMembro());
	}

	public static List<FuncaoDto> converter(List<Funcao> funcaoDto) {
		return funcaoDto.stream().map(FuncaoDto::new).collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public MembroDto getMembro() {
		return membro;
	}

	public void setMembro(MembroDto membro) {
		this.membro = membro;
	}
}
