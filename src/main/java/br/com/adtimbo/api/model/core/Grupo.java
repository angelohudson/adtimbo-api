package br.com.adtimbo.api.model.core;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_grupo")
public class Grupo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "titulo")
	private String titulo;

	@ManyToMany(mappedBy = "grupos")
	private List<Membro> membros;

	@ManyToOne
	@JoinColumn(name = "ministerio_id", referencedColumnName = "id")
	private Ministerio ministerio = new Ministerio();

	public void addMembros(Membro membro) {
		this.membros.add(membro);
	}

	public void setMembros(List<Membro> membros) {
		this.membros = membros;
	}

	public List<Membro> getMembros() {
		return membros;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdMinisterio() {
		return this.ministerio.getId();
	}

	public void setIdMinisterio(Long ministerioId) {
		this.ministerio.setId(ministerioId);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Ministerio getMinisterio() {
		return ministerio;
	}

	public void setMinisterio(Ministerio ministerio) {
		this.ministerio = ministerio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grupo other = (Grupo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
