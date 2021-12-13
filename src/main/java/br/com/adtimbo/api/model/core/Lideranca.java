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
@Table(name = "tb_lideranca")
public class Lideranca {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "membro_id", referencedColumnName = "id")
	private Membro membro;

	@ManyToOne
	@JoinColumn(name = "ministerio_id", referencedColumnName = "id")
	private Ministerio ministerio;

	public Lideranca() {
	}

	public Lideranca(Membro membro, Ministerio ministerio) {
		this.setMinisterio(ministerio);
		this.setMembro(membro);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ministerio getMinisterio() {
		return ministerio;
	}

	public void setMinisterio(Ministerio ministerio) {
		this.ministerio = ministerio;
	}

	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}

}
