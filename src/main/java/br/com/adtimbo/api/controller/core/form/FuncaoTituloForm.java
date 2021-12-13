package br.com.adtimbo.api.controller.core.form;

import br.com.adtimbo.api.model.core.FuncaoTitulo;

public class FuncaoTituloForm {

	private String titulo;
	private Long ministerioId;

	public FuncaoTitulo converter() {
		FuncaoTitulo funcaoTitulo = new FuncaoTitulo();
		funcaoTitulo.setTitulo(titulo);
		funcaoTitulo.setIdMinisterio(ministerioId);
		return funcaoTitulo;
	}

	public Long getMinisterioId() {
		return ministerioId;
	}

	public void setMinisterioId(Long ministerioId) {
		this.ministerioId = ministerioId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
