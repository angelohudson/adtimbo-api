package br.com.adtimbo.api.controller.core.form;

import java.util.List;

public class LiderancaForm {

	private List<Integer> membros;
	private Long ministerioId;

	public List<Integer> getMembros() {
		return membros;
	}

	public void setMembros(List<Integer> membros) {
		this.membros = membros;
	}

	public Long getMinisterioId() {
		return ministerioId;
	}

	public void setMinisterioId(Long ministerioId) {
		this.ministerioId = ministerioId;
	}

}
