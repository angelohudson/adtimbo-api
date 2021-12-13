package br.com.adtimbo.api.controller.ebd.form;

import br.com.adtimbo.api.model.ebd.core.Classe;

public class ClasseForm {
	public Integer id;
	public Integer dataFinal;
	public Integer dataInicial;
	public String titulo;

	public Classe converter() {
		Classe classe = new Classe();
		classe.setDataFinal(dataFinal);
		classe.setDataInicial(dataInicial);
		classe.setTitulo(titulo);
		return classe;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Integer dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Integer getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Integer dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
