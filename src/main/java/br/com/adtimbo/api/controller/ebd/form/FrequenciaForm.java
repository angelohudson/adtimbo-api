package br.com.adtimbo.api.controller.ebd.form;

import br.com.adtimbo.api.model.ebd.aula.Frequencia;
import br.com.adtimbo.api.service.ebd.AlunoService;

public class FrequenciaForm {

	private Integer aluno;
	private Boolean presenca;

	public Frequencia converter(AlunoService alunoService) {
		Frequencia frequencia = new Frequencia();
		frequencia.setAluno(alunoService.findOne(this.aluno).get());
		frequencia.setPresenca(this.presenca);
		return frequencia;
	}

	public Integer getAluno() {
		return aluno;
	}

	public void setAluno(Integer aluno) {
		this.aluno = aluno;
	}

	public Boolean getPresenca() {
		return presenca;
	}

	public void setPresenca(Boolean presenca) {
		this.presenca = presenca;
	}
	
}
