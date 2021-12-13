package br.com.adtimbo.api.controller.task.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.controller.core.dto.MinisterioDto;
import br.com.adtimbo.api.model.core.Membro;
import br.com.adtimbo.api.model.task.Comentario;
import br.com.adtimbo.api.model.task.Evento;
import br.com.adtimbo.api.model.task.Task;
import br.com.adtimbo.api.model.task.TaskStatus;
import br.com.adtimbo.api.model.task.TipoEvento;
import lombok.Data;

@Data
public class EventoDto {

	private Long id;
	private String titulo;
	private String subtitulo;
	private String descricao;
	private String tipo;
	private LocalDateTime data;
	private LocalDateTime dataFim;
	private LocalDateTime dataCriacao;
	private MinisterioDto ministerio;
	private List<EventTaskDto> tasksDto;
	private List<ComentarioDto> comentarios;

	public EventoDto(Evento evento) {
		this.id = evento.getId();
		this.titulo = evento.getTitulo();
		this.subtitulo = evento.getSubtitulo();
		this.descricao = evento.getDescricao();
		this.data = evento.getDataInicio();
		this.dataFim = evento.getDataFim();
		this.dataCriacao = evento.getDataCriacao();
		this.ministerio = new MinisterioDto(evento.getMinisterio());
		if (evento.getTasks() != null && evento.getTipo() != null && evento.getTipo().equals(TipoEvento.ESCALA))
			this.tasksDto = EventTaskDto.converter(evento.getTasks());
		if (evento.getTipo() != null)
			this.tipo = evento.getTipo().toString();
		if (evento.getComentario() != null)
			this.comentarios = evento.getComentario().stream().map(c -> new ComentarioDto(c))
					.collect(Collectors.toList());
	}

	public static List<EventoDto> converter(List<Evento> eventos) {
		return eventos.stream().map(EventoDto::new).collect(Collectors.toList());
	}
}

@Data
class ComentarioDto {
	private String memberName;
	private String comentario;

	public ComentarioDto(Comentario comentario) {
		this.memberName = comentario.getMembro().getNome();
		this.comentario = comentario.getComentario();
	}
}

@Data
class EventTaskDto {
	private Long id;
	private String justificativa;
	private String funcaoEscala;
	private TaskStatus status;
	private EventMembroDto membro;

	public static List<EventTaskDto> converter(List<Task> tasks) {
		return tasks.stream().map(t -> new EventTaskDto(t)).collect(Collectors.toList());
	}

	public EventTaskDto(Task task) {
		this.id = task.getId();
		this.justificativa = task.getJustificativa();
		this.status = task.getStatus();
		this.setFuncaoEscala(task.getFuncaoEscala());
		if (task.getMembro() != null)
			this.membro = new EventMembroDto(task.getMembro());
	}
}

@Data
class EventMembroDto {
	private Integer id;
	private String nome;
	private String telefone;

	public EventMembroDto(Membro membro) {
		this.id = membro.getId();
		this.nome = membro.getNome();
		this.telefone = membro.getTelefone();
	}
}