package br.com.adtimbo.api.model.core;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.adtimbo.api.model.auth.Role;

@Entity
@Table(name = "tb_funcao_titulo")
public class FuncaoTitulo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "titulo")
	private String titulo;

	@ManyToOne
	@JoinColumn(name = "ministerio_id", referencedColumnName = "id")
	private Ministerio ministerio = new Ministerio();

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role = new Role();

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getRoleId() {
		return role.getId();
	}

	public Ministerio getMinisterio() {
		return ministerio;
	}

	public void setMinisterio(Ministerio ministerio) {
		this.ministerio = ministerio;
	}

	public Long getIdMinisterio() {
		return ministerio.getId();
	}

	public void setIdMinisterio(Long id) {
		this.ministerio.setId(id);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.role.setDescription("ROLE_" + titulo.toUpperCase());
		this.titulo = titulo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}