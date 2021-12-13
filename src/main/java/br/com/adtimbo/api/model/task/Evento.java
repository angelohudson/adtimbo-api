package br.com.adtimbo.api.model.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.adtimbo.api.model.core.Membro;
import br.com.adtimbo.api.model.core.Ministerio;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_evento")
public class Evento {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "titulo", length = 50)
	private String titulo;

	@Column(name = "subtitulo", length = 100)
	private String subtitulo;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "data_inicio")
	private LocalDateTime dataInicio;

	@Column(name = "data_fim")
	private LocalDateTime dataFim;

	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;

	@ManyToOne
	@JoinColumn(name = "ministerio_id", referencedColumnName = "id")
	private Ministerio ministerio = new Ministerio();

	@OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
	private List<Task> tasks;

	@ManyToOne
	@JoinColumn(name = "criador_id", referencedColumnName = "id")
	private Membro criador;

	@OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
	private List<Comentario> comentario;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoEvento tipo;

	public Evento() {
		this.comentario = new ArrayList<Comentario>();
	}

	public void setIdMinisterio(Long ministerioId) {
		this.ministerio.setId(ministerioId);
	}

	public void appendComentario(String comentarioStr, Membro membro) {
		Comentario comentario = new Comentario(comentarioStr, this, membro);
		this.comentario.add(comentario);
	}

	public void appendTasks(List<Task> tasks) {
		tasks.forEach(t -> t.setEvento(this));
		this.tasks.addAll(tasks);
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
		this.tasks.forEach(t -> t.setEvento(this));
	}

	@Override
	public String toString() {
		return "Evento [id=" + id + ", titulo=" + titulo + ", subtitulo=" + subtitulo + ", descricao=" + descricao
				+ ", dataInicio=" + dataInicio + ", dataFim=" + dataFim + ", dataCriacao=" + dataCriacao
				+ ", ministerio=" + ministerio + ", criador=" + criador + "]" + ", envolvidos=" + this.tasks.size();
	}

}