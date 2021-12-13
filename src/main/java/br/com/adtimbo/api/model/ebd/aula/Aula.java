package br.com.adtimbo.api.model.ebd.aula;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.adtimbo.api.model.ebd.core.Classe;
import br.com.adtimbo.api.model.ebd.funcao.Professor;

@Entity
@Table(name = "tb_aula")
public class Aula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "valor_oferta")
	private Float valorOferta;

	@Column(name = "data_aula")
	private LocalDate dataAula;

	@Column(name = "frequencia_realizada")
	private Boolean frequenciaRealizada;

	@ManyToOne
	@JoinColumn(name = "professor", referencedColumnName = "id")
	private Professor professor;

	@Column(name = "tema")
	private String tema;

	@ManyToOne
	@JoinColumn(name = "classe", referencedColumnName = "id")
	private Classe classe;

	@OneToMany(mappedBy = "aula", cascade = CascadeType.ALL)
	private List<Frequencia> frequencia = new ArrayList<Frequencia>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Float getValorOferta() {
		return valorOferta;
	}

	public void setValorOferta(Float valorOferta) {
		this.valorOferta = valorOferta;
	}

	public LocalDate getDataAula() {
		return dataAula;
	}

	public void setDataAula(LocalDate dataAula) {
		this.dataAula = dataAula;
	}

	public Boolean getFrequenciaRealizada() {
		return frequenciaRealizada;
	}

	public void setFrequenciaRealizada(Boolean frequenciaRealizada) {
		this.frequenciaRealizada = frequenciaRealizada;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public List<Frequencia> getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(List<Frequencia> frequencia) {
		this.frequencia = frequencia;
	}

	public void appendFrequencia(Frequencia frequencia) {
		frequencia.setAula(this);
		this.frequencia.add(frequencia);
	}
}
