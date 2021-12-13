package br.com.adtimbo.api.controller.core.form;

import java.util.List;

public class GrupoMembroForm {
	private List<Integer> membros;
	private Long grupoId;

	public List<Integer> getMembros() {
		return membros;
	}

	public void setMembros(List<Integer> membros) {
		this.membros = membros;
	}

	public Long getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(Long grupoId) {
		this.grupoId = grupoId;
	}

}
