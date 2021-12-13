package br.com.adtimbo.api.controller.ebd.form;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.model.ebd.aula.Aula;
import br.com.adtimbo.api.model.ebd.aula.Frequencia;
import br.com.adtimbo.api.service.ebd.AlunoService;
import br.com.adtimbo.api.service.ebd.ClasseService;
import br.com.adtimbo.api.service.ebd.ProfessorService;

public class AulaForm {
	private Integer id;
	private String dataAula;
	private Integer professor;
	private Integer classe;
	private String tema;
	private String descricao;
	private List<FrequenciaForm> frequencia;
	private Boolean frequenciaRealizada;
	private Float valorOferta;

	public Aula converter(ClasseService classeService, ProfessorService professorService, AlunoService alunoService) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Aula aula = new Aula();
		aula.setProfessor(professorService.findOne(professor).get());
		aula.setClasse(classeService.findOne(this.classe).get());
		aula.setDataAula(LocalDate.parse(this.dataAula, formatter));
		aula.setTema(this.tema);
		aula.setDescricao(this.descricao);
		this.frequencia.stream().forEach(f -> aula.appendFrequencia(f.converter(alunoService)));
		aula.setFrequenciaRealizada(this.frequenciaRealizada);
		aula.setValorOferta(this.valorOferta);

		return aula;
	}

	public Aula converter(Aula aula) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		aula.setDataAula(LocalDate.parse(this.dataAula, formatter));
		aula.setTema(this.tema);
		aula.setDescricao(this.descricao);
		List<Frequencia> frequencia = aula.getFrequencia().stream().map(this::updateFrequencia)
				.collect(Collectors.toList());
		aula.setFrequencia(frequencia);
		aula.setFrequenciaRealizada(this.frequenciaRealizada);
		aula.setValorOferta(this.valorOferta);
		return aula;
	}

	private Frequencia updateFrequencia(Frequencia frequencia) {
		List<FrequenciaForm> filter = this.frequencia.stream()
				.filter(f -> frequencia.getAluno().getId() == f.getAluno()).collect(Collectors.toList());
		FrequenciaForm frequenciaForm = filter.get(0);
		frequencia.setPresenca(frequenciaForm.getPresenca());
		return frequencia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataAula() {
		return dataAula;
	}

	public void setDataAula(String dataAula) {
		this.dataAula = dataAula;
	}

	public Integer getProfessor() {
		return professor;
	}

	public void setProfessor(Integer professor) {
		this.professor = professor;
	}

	public Integer getClasse() {
		return classe;
	}

	public void setClasse(Integer classe) {
		this.classe = classe;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<FrequenciaForm> getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(List<FrequenciaForm> frequencia) {
		this.frequencia = frequencia;
	}

	public Boolean getFrequenciaRealizada() {
		return frequenciaRealizada;
	}

	public void setFrequenciaRealizada(Boolean frequenciaRealizada) {
		this.frequenciaRealizada = frequenciaRealizada;
	}

	public Float getValorOferta() {
		return valorOferta;
	}

	public void setValorOferta(Float valorOferta) {
		this.valorOferta = valorOferta;
	}

}
