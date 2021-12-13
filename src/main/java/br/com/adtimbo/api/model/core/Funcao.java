package br.com.adtimbo.api.model.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_funcao")
public class Funcao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "funcao_id", referencedColumnName = "id")
	private FuncaoTitulo funcaoTitulo = new FuncaoTitulo();

	@ManyToOne
	@JoinColumn(name = "membro", referencedColumnName = "id")
	private Membro membro = new Membro();

	@Column(name = "ativo")
	private Boolean ativo = true;

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Funcao(Membro membro) {
		this.membro = membro;
	}

	public Funcao() {
	}

	public FuncaoTitulo getFuncaoTitulo() {
		return funcaoTitulo;
	}

	public void setFuncaoTitulo(FuncaoTitulo funcaoTitulo) {
		this.funcaoTitulo = funcaoTitulo;
	}

	public Integer getIdFuncaoTitulo() {
		return funcaoTitulo.getId();
	}

	public void setIdFuncaoTitulo(Integer id) {
		this.funcaoTitulo.setId(id);
	}

	public String getTipo() {
		return this.funcaoTitulo.getTitulo();
	}

	public void setTipo(Integer tituloId) {
		this.funcaoTitulo.setId(tituloId);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}

	public Integer getIdMembro() {
		return membro.getId();
	}

	public void setIdMembro(Integer id) {
		this.membro.setId(id);
	}

}