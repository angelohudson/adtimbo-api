package br.com.adtimbo.api.controller.ebd.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.controller.core.dto.FuncaoDto;
import br.com.adtimbo.api.model.ebd.funcao.Professor;

public class ProfessorDto {
	public Integer id;
	public FuncaoDto funcao;
	public List<ClasseDto> classes;

	public ProfessorDto(Professor professor) {
		this.id = professor.getId();
		this.funcao = new FuncaoDto(professor.getFuncao());
		this.classes = professor.getClasses().stream().map(ClasseDto::new).collect(Collectors.toList());
	}

	public static List<ProfessorDto> converter(List<Professor> professorDto) {
		return professorDto.stream().map(ProfessorDto::new).collect(Collectors.toList());
	}
}
