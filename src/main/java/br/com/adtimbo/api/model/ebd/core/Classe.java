package br.com.adtimbo.api.model.ebd.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_classe")
public class Classe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "data_inicial")
	private Integer dataInicial;

	@Column(name = "data_final")
	private Integer dataFinal;

	@Column(name = "titulo")
	private String titulo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Integer dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Integer getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Integer dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
}
