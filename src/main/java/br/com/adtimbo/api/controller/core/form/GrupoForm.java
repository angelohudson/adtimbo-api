package br.com.adtimbo.api.controller.core.form;

import br.com.adtimbo.api.model.core.Grupo;

public class GrupoForm {

	private String titulo;
	private Long ministerioId;

	public Grupo converter() {
		Grupo grupo = new Grupo();

		grupo.setTitulo(titulo);
		grupo.setIdMinisterio(ministerioId);

		return grupo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Long getMinisterioId() {
		return ministerioId;
	}

	public void setMinisterioId(Long ministerioId) {
		this.ministerioId = ministerioId;
	}
}
