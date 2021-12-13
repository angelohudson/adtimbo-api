package br.com.adtimbo.api.controller.ebd.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.model.ebd.aula.Aula;

public class AulaDto {
	private Integer id;
	private String dataAula;
	private Integer professor;
	private ClasseDto classe;
	private String tema;
	private String descricao;
	private List<FrequenciaDto> frequencia;
	private Boolean frequenciaRealizada;
	private Float valorOferta;

	public AulaDto(Aula aula) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.id = aula.getId();
		dataAula = formatter.format(aula.getDataAula());
		professor = aula.getProfessor().getId();
		classe = new ClasseDto(aula.getClasse());
		tema = aula.getTema();
		descricao = aula.getDescricao();
		frequencia = aula.getFrequencia().stream().map(FrequenciaDto::new).collect(Collectors.toList());
		frequenciaRealizada = aula.getFrequenciaRealizada();
		valorOferta = aula.getValorOferta();
	}

	public static List<AulaDto> converter(List<Aula> aulaDto) {
		return aulaDto.stream().map(AulaDto::new).collect(Collectors.toList());
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

	public ClasseDto getClasse() {
		return classe;
	}

	public void setClasse(ClasseDto classe) {
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

	public List<FrequenciaDto> getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(List<FrequenciaDto> frequencia) {
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
