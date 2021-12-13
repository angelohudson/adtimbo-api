package br.com.adtimbo.api.controller.ebd.dto;

import br.com.adtimbo.api.model.ebd.aula.Frequencia;

public class FrequenciaDto {

	private AlunoDto aluno;
	private Boolean presenca;

	public FrequenciaDto(Frequencia frequencia) {
		this.aluno = new AlunoDto(frequencia.getAluno());
		this.presenca = frequencia.getPresenca();
	}

	public AlunoDto getAluno() {
		return aluno;
	}

	public void setAluno(AlunoDto aluno) {
		this.aluno = aluno;
	}

	public Boolean getPresenca() {
		return presenca;
	}

	public void setPresenca(Boolean presenca) {
		this.presenca = presenca;
	}

}
