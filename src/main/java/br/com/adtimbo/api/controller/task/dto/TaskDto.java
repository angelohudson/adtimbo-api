package br.com.adtimbo.api.controller.task.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.controller.core.dto.MembroDto;
import br.com.adtimbo.api.model.task.Task;
import br.com.adtimbo.api.model.task.TaskStatus;
import lombok.Data;

@Data
public class TaskDto {

	private Long id;
	private String subtitulo;
	private String justificativa;
	private String funcaoEscala;
	private TaskStatus status;
	private MembroDto membro;
	private EventoDto evento;

	public TaskDto(Task task) {
		this.id = task.getId();
		this.subtitulo = task.getSubtitulo();
		this.justificativa = task.getJustificativa();
		this.status = task.getStatus();
		this.setFuncaoEscala(task.getFuncaoEscala());

		if (task.getMembro() != null)
			this.membro = new MembroDto(task.getMembro());

		if (task.getEvento() != null) {
			this.evento = new EventoDto(task.getEvento());
			this.evento.setTasksDto(null);
		}
	}

	public static List<TaskDto> converter(List<Task> tasks) {
		return tasks.stream().map(t -> new TaskDto(t)).collect(Collectors.toList());
	}
}
