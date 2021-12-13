package br.com.adtimbo.api.controller.ebd.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.controller.core.dto.FuncaoDto;
import br.com.adtimbo.api.model.ebd.funcao.Aluno;

public class AlunoDto {
	public Integer id;
	public FuncaoDto funcao;
	public ClasseDto classe;

	public AlunoDto(Aluno aluno) {
		this.id = aluno.getId();
		this.funcao = new FuncaoDto(aluno.getFuncao());
		this.classe = new ClasseDto(aluno.getClasse());
	}

	public static List<AlunoDto> converter(List<Aluno> alunoDto) {
		return alunoDto.stream().map(AlunoDto::new).collect(Collectors.toList());
	}
}
