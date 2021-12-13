package br.com.adtimbo.api.controller.core.form;

import br.com.adtimbo.api.model.core.Ministerio;

public class MinisterioForm {

	private String titulo;
	private String cor;

	public Ministerio converter() {
		Ministerio ministerio = new Ministerio();
		ministerio.setTitulo(titulo);
		ministerio.setCor(cor);
		return ministerio;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

}
