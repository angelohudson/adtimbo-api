package br.com.adtimbo.api.controller.core.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.model.core.Lideranca;

public class LiderancaDto {

	MembroDto membroDto;
	MinisterioDto ministerioDto;
	private Long id;

	public LiderancaDto(Lideranca lideranca) {
		this.id = lideranca.getId();
		this.membroDto = new MembroDto(lideranca.getMembro());
		this.ministerioDto = new MinisterioDto(lideranca.getMinisterio());
	}

	public static List<LiderancaDto> converter(List<Lideranca> liderancas) {
		return liderancas.stream().map(LiderancaDto::new).collect(Collectors.toList());
	}

	public MembroDto getMembroDto() {
		return membroDto;
	}

	public void setMembroDto(MembroDto membroDto) {
		this.membroDto = membroDto;
	}

	public MinisterioDto getMinisterioDto() {
		return ministerioDto;
	}

	public void setMinisterioDto(MinisterioDto ministerioDto) {
		this.ministerioDto = ministerioDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
