package br.com.adtimbo.api.controller.ebd.form;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.adtimbo.api.model.core.Funcao;
import br.com.adtimbo.api.model.core.Membro;
import br.com.adtimbo.api.model.ebd.core.Classe;
import br.com.adtimbo.api.model.ebd.funcao.FuncaoEbd;
import br.com.adtimbo.api.model.ebd.funcao.Professor;
import br.com.adtimbo.api.service.core.MembroService;
import br.com.adtimbo.api.service.ebd.ClasseService;

public class ProfessorForm {
	private Integer membro;
	private List<Integer> classes;

	public Professor converter(ClasseService classeService, MembroService membroService) {
		Optional<Membro> membroOp = membroService.findOne(this.membro);

		Professor professor = new Professor();
		professor.setClasses(this.getClasses(classeService));

		Funcao funcao = new Funcao(membroOp.get());
		funcao.setTipo(FuncaoEbd.PROFESSOR.getCod());

		professor.setFuncao(funcao);

		return professor;
	}

	public Professor converter(ClasseService classeService, Professor professor) {
		professor.setClasses(this.getClasses(classeService));
		return professor;
	}

	private List<Classe> getClasses(ClasseService classeService) {
		return this.classes.stream().map(classeId -> {
			return classeService.findOne(classeId).get();
		}).collect(Collectors.toList());
	}

	public Integer getMembro() {
		return membro;
	}

	public void setMembro(Integer membro) {
		this.membro = membro;
	}

	public List<Integer> getClasses() {
		return classes;
	}

	public void setClasses(List<Integer> classes) {
		this.classes = classes;
	}
}
