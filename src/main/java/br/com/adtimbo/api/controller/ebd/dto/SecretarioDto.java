package br.com.adtimbo.api.controller.ebd.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.controller.core.dto.FuncaoDto;
import br.com.adtimbo.api.model.ebd.funcao.Secretario;

public class SecretarioDto {
	public Integer id;
	public FuncaoDto funcao;
	public List<ClasseDto> classes;

	public SecretarioDto(Secretario secretario) {
		this.id = secretario.getId();
		this.funcao = new FuncaoDto(secretario.getFuncao());
		this.classes = secretario.getClasses().stream().map(ClasseDto::new).collect(Collectors.toList());
	}

	public static List<SecretarioDto> converter(List<Secretario> secretarioDto) {
		return secretarioDto.stream().map(SecretarioDto::new).collect(Collectors.toList());
	}
}
