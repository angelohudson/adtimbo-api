package br.com.adtimbo.api.controller.ebd.form;

import java.util.Optional;

import br.com.adtimbo.api.model.core.Funcao;
import br.com.adtimbo.api.model.core.Membro;
import br.com.adtimbo.api.model.ebd.core.Classe;
import br.com.adtimbo.api.model.ebd.funcao.Aluno;
import br.com.adtimbo.api.model.ebd.funcao.FuncaoEbd;
import br.com.adtimbo.api.service.core.MembroService;
import br.com.adtimbo.api.service.ebd.ClasseService;

public class AlunoForm {
	private Integer membro;
	private Integer classe;

	public Aluno converter(ClasseService classeService, MembroService membroService) {
		Optional<Membro> membroOp = membroService.findOne(this.membro);
		Aluno aluno = new Aluno();
		Optional<Classe> classe = classeService.findOne(this.classe);
		aluno.setClasse(classe.get());
		Funcao funcao = new Funcao(membroOp.get());
		funcao.setTipo(FuncaoEbd.ALUNO.getCod());
		aluno.setFuncao(funcao);
		return aluno;
	}

	public Aluno converter(ClasseService classeService, Aluno aluno) {
		Optional<Classe> classe = classeService.findOne(this.classe);
		aluno.setClasse(classe.get());
		return aluno;
	}

	public Integer getMembro() {
		return membro;
	}

	public void setMembro(Integer membro) {
		this.membro = membro;
	}

	public Integer getClasse() {
		return classe;
	}

	public void setClasse(Integer classes) {
		this.classe = classes;
	}
}
