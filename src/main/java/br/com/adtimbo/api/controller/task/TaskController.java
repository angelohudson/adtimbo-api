package br.com.adtimbo.api.controller.task;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.adtimbo.api.controller.task.dto.TaskDto;
import br.com.adtimbo.api.controller.task.form.EscalaForm;
import br.com.adtimbo.api.controller.task.form.EventoForm;
import br.com.adtimbo.api.controller.task.form.JustificativaForm;
import br.com.adtimbo.api.model.task.Evento;
import br.com.adtimbo.api.model.task.Task;
import br.com.adtimbo.api.service.task.EventoService;
import br.com.adtimbo.api.service.task.TaskService;

@RestController
@RequestMapping(value = "/task", produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = "*")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private EventoService eventoService;

	@PostMapping("/by-grupo/{grupo-id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<Object> criarPorGrupo(@PathVariable("grupo-id") Long grupoId,
			@RequestBody EventoForm taskForm) {
		try {
			Evento evento = taskForm.converter();
			if (this.eventoService.saveGrupoTask(evento, grupoId)) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (DateTimeParseException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/by-ministerio/{ministerio-id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<Object> criarPorMinisterio(@PathVariable("ministerio-id") Long ministerioId,
			@RequestBody EventoForm taskForm) {
		Evento evento = taskForm.converter();
		if (this.eventoService.saveMinisterioTask(evento, ministerioId)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/by-escala/{ministerio-id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<List<TaskDto>> criarPorEscala(@PathVariable("ministerio-id") Long ministerioId,
			@RequestBody EscalaForm taskForm) {
		List<Task> tasks = taskForm.converter();
		Evento evento = taskForm.getTask().converter();
		List<Task> membrosOcupados = this.eventoService.saveByEscala(evento, tasks, ministerioId);
		return ResponseEntity.ok(membrosOcupados.stream().map(TaskDto::new).collect(Collectors.toList()));
	}

	@PutMapping("/confirma-task/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<TaskDto> confirmaTask(@PathVariable Long id) {
		Optional<Task> task = this.taskService.confirmTask(id);
		if (task.isPresent()) {
			return ResponseEntity.ok(new TaskDto(task.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/cancela-task/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<TaskDto> cancelaTask(@PathVariable Long id, @RequestBody JustificativaForm form) {
		Optional<Task> task = this.taskService.cancelaTask(id, form.getJustificativa());
		if (task.isPresent()) {
			return ResponseEntity.ok(new TaskDto(task.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/by-periodo/{ministerio-id}")
	@PreAuthorize("isAuthenticated()")
	public List<TaskDto> lista(UriComponentsBuilder uriBuilder, @PathVariable("ministerio-id") Long ministerioId,
			@RequestParam("dataInicio") String dataInicio, @RequestParam("dataFim") String dataFim) {
		List<Task> professorDto = this.taskService.findByMinisterioAndPeriodo(ministerioId, dataInicio, dataFim);
		return TaskDto.converter(professorDto);
	}

	@GetMapping("/by-periodo")
	@PreAuthorize("isAuthenticated()")
	public List<TaskDto> lista(UriComponentsBuilder uriBuilder, @RequestParam("dataInicio") String dataInicio,
			@RequestParam("dataFim") String dataFim) {
		List<Task> professorDto = this.taskService.findByPeriodo(dataInicio, dataFim);
		return TaskDto.converter(professorDto);
	}

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<TaskDto> lista(UriComponentsBuilder uriBuilder) {
		List<Task> professorDto = this.taskService.findAll();
		return TaskDto.converter(professorDto);
	}

	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<TaskDto> detalhar(@PathVariable Long id) {
		Optional<Task> optional = this.taskService.findOne(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new TaskDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}

}
