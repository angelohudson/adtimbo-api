package br.com.adtimbo.api.model.ebd.funcao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.adtimbo.api.model.core.Funcao;
import br.com.adtimbo.api.model.ebd.core.Classe;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_secretario")
public class Secretario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "funcao", referencedColumnName = "id")
	private Funcao funcao;

	@ManyToMany
	@JoinTable(name = "tb_secretario_classe", 
	joinColumns = {@JoinColumn(name = "secretario", referencedColumnName = "id") }, 
	inverseJoinColumns = {@JoinColumn(name = "classe", referencedColumnName = "id") })
	private List<Classe> classes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}

}
