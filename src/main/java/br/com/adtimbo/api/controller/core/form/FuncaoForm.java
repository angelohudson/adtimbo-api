package br.com.adtimbo.api.controller.core.form;

import java.util.ArrayList;
import java.util.List;

import br.com.adtimbo.api.model.core.Funcao;

public class FuncaoForm {
	private Integer funcaoTituloId;
	private List<Integer> membros;

	public List<Funcao> converter() {
		List<Funcao> funcaos = new ArrayList<>();

		this.membros.stream().forEach(membrosId -> {
			Funcao funcao = new Funcao();
			funcao.setIdMembro(membrosId);
			funcao.setIdFuncaoTitulo(funcaoTituloId);
			funcaos.add(funcao);
		});

		return funcaos;
	}

	public List<Integer> getMembros() {
		return membros;
	}

	public void setMembros(List<Integer> membros) {
		this.membros = membros;
	}

	public Integer getFuncaoTituloId() {
		return funcaoTituloId;
	}

	public void setFuncaoTituloId(Integer funcaoTituloId) {
		this.funcaoTituloId = funcaoTituloId;
	}

}
