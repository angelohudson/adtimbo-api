package br.com.adtimbo.api.controller.task;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.adtimbo.api.controller.task.dto.EventoDto;
import br.com.adtimbo.api.controller.task.dto.TaskDto;
import br.com.adtimbo.api.controller.task.form.ComentarioForm;
import br.com.adtimbo.api.controller.task.form.EscalaForm;
import br.com.adtimbo.api.model.task.Evento;
import br.com.adtimbo.api.model.task.Task;
import br.com.adtimbo.api.service.auth.AuthenticationService;
import br.com.adtimbo.api.service.task.EventoService;
import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/evento", produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = "*")
public class EventoController {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private AuthenticationService authenticationService;

	@GetMapping("/by-periodo/{ministerio-id}")
	@PreAuthorize("isAuthenticated()")
	public List<EventoDto> lista(UriComponentsBuilder uriBuilder, @PathVariable("ministerio-id") Long ministerioId,
			@RequestParam("dataInicio") String dataInicio, @RequestParam("dataFim") String dataFim) {
		List<Evento> evento = this.eventoService.findByMinisterioAndPeriodo(ministerioId, dataInicio, dataFim);
		return EventoDto.converter(evento);
	}

	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<EventoDto> lista(UriComponentsBuilder uriBuilder, @PathVariable("id") Long id) {
		Optional<Evento> evento = this.eventoService.findOne(id);
		if (evento.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(new EventoDto(evento.get()));
	}

	@DeleteMapping("/{evento-id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<?> remover(@PathVariable("evento-id") Long id) {
		Boolean success = this.eventoService.remover(id);
		if (success)
			return ResponseEntity.ok().build();
		else
			return ResponseEntity.notFound().build();
	}

	@PutMapping("/{evento-id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<List<TaskDto>> atualiza(@PathVariable("evento-id") Long eventoId,
			@RequestBody EscalaForm taskForm) {
		try {
			List<Task> membrosOcupados;
			membrosOcupados = this.eventoService.updateEvento(taskForm.getEvento(), eventoId);
			return ResponseEntity.ok(membrosOcupados.stream().map(TaskDto::new).collect(Collectors.toList()));
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("{evento-id}/comentario")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<Object> adicionarComentario(@PathVariable("evento-id") Long eventoId,
			@RequestBody ComentarioForm comentarioForm) {
		try {
			this.eventoService.addComentario(comentarioForm.getComentario(), eventoId,
					authenticationService.getMembroLogged());
			return ResponseEntity.ok().build();
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
