package br.com.adtimbo.api.service.task;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adtimbo.api.model.core.Grupo;
import br.com.adtimbo.api.model.core.Membro;
import br.com.adtimbo.api.model.core.Ministerio;
import br.com.adtimbo.api.model.task.Evento;
import br.com.adtimbo.api.model.task.Task;
import br.com.adtimbo.api.model.task.TaskStatus;
import br.com.adtimbo.api.repository.task.EventoRepository;
import br.com.adtimbo.api.repository.task.TaskRepository;
import br.com.adtimbo.api.service.auth.AuthenticationService;
import br.com.adtimbo.api.service.core.GrupoService;
import br.com.adtimbo.api.service.core.MembroService;
import br.com.adtimbo.api.service.core.MinisterioService;
import br.com.adtimbo.api.service.notification.NotificationService;
import javassist.NotFoundException;

@Service
public class EventoService {

	@Autowired
	EventoRepository repository;

	@Autowired
	private MembroService membroService;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private MinisterioService ministerioService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private EventoRepository eventoRepository;

	final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

	public List<Evento> findByMinisterioAndPeriodo(Long ministerioId, String dataInicio, String dataFim) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneOffset.UTC);
		LocalDateTime beginDate = LocalDateTime.parse(dataInicio, formatter);
		LocalDateTime endDate = LocalDateTime.parse(dataFim, formatter);
		return this.repository.findByMinisterioIdAndDataInicioBetween(ministerioId, beginDate, endDate);
	}

	public Optional<Evento> findOne(Long id) {
		return this.repository.findById(id);
	}

	public Boolean remover(Long id) {
		Optional<Evento> evento = this.findOne(id);
		if (evento.isPresent())
			this.repository.delete(evento.get());
		return evento.isPresent();
	}

	public void addComentario(String comentario, Long eventoId, Membro membro) throws NotFoundException {
		Optional<Evento> found = this.repository.findById(eventoId);
		if (found.isEmpty())
			throw new NotFoundException("Registro não encontrado");
		Evento evento = found.get();
		List<String> tokens = evento.getTasks().stream().filter(t -> t.getMembro().getId() != membro.getId())
				.map(t -> t.getMembro().getDeviceToken()).filter(t -> t != null).collect(Collectors.toList());
		Map<String, String> map = new HashMap<>();
		map.put("topico", "novoComentario");
		map.put("membroNome", membro.getNome());
		map.put("eventId", evento.getId().toString());
		if (tokens.size() > 0)
			this.notificationService.sendAll("Mensagem de " + membro.getNome(), comentario, tokens, map);
		evento.appendComentario(comentario, membro);
		this.repository.save(evento);
	}

	// Criação e modificação de Eventos

	public Boolean saveGrupoTask(Evento evento, Long grupoId) {
		Optional<Grupo> op = this.grupoService.findOne(grupoId);
		if (op.isEmpty())
			return false;
		Grupo grupo = op.get();
		evento.setMinisterio(grupo.getMinisterio());
		List<Task> tasks = this.bindTasks(evento, grupo.getMembros());
		evento.setTasks(tasks);
		saveEvento(evento);
		this.notificationService.sendTasks(evento.getTitulo(), evento.getSubtitulo(), tasks);
		return true;
	}

	public Boolean saveMinisterioTask(Evento evento, Long ministerioId) {
		Optional<Ministerio> op = this.ministerioService.findOne(ministerioId);
		if (op.isEmpty())
			return false;
		Ministerio ministerio = op.get();
		evento.setMinisterio(ministerio);
		List<Task> tasks = this.bindTasks(evento, ministerio.getMembros());
		evento.setTasks(tasks);
		saveEvento(evento);
		this.notificationService.sendTasks(evento.getTitulo(), evento.getSubtitulo(), tasks);
		return true;
	}

	public List<Task> saveByEscala(Evento evento, List<Task> tasks, Long ministerioId) {
		List<Task> membrosOcupados = new ArrayList<>();

		List<Task> validTasks = tasks.stream().filter(t -> {
			Optional<Task> findMembrosOcupados = this.taskRepository.findMembrosOcupados(t.getMembroId(),
					evento.getDataInicio(), evento.getDataFim());
			if (findMembrosOcupados.isEmpty())
				return true;
			membrosOcupados.add(findMembrosOcupados.get());
			return false;
		}).collect(Collectors.toList());

		validTasks.forEach(t -> {
			Optional<Membro> membroOp = this.membroService.findOne(t.getMembroId());
			if (membroOp.isEmpty())
				return;
			Membro membro = membroOp.get();
			t.setStatus(TaskStatus.DEFAULT);
			t.setMembro(membro);
		});
		evento.setIdMinisterio(ministerioId);
		evento.setTasks(validTasks);
		if (!validTasks.isEmpty())
			saveEvento(evento);
		this.notificationService.sendSchedulers(validTasks);
		return membrosOcupados;
	}

	public List<Task> updateEvento(Evento evento, Long id) throws NotFoundException {
		Optional<Evento> found = this.repository.findById(id);
		if (found.isEmpty())
			throw new NotFoundException("Registro não encontrado");

		List<Task> membrosOcupados = new ArrayList<Task>();
		List<Task> validTasks = evento.getTasks().stream().filter(t -> {
			Optional<Task> findMembrosOcupados = this.taskRepository.findMembrosOcupados(t.getMembroId(),
					evento.getDataInicio(), evento.getDataFim());
			if (findMembrosOcupados.isEmpty())
				return true;
			membrosOcupados.add(findMembrosOcupados.get());
			return false;
		}).collect(Collectors.toList());

		validTasks.forEach(this::bindMember);
		Evento old = found.get();
		old.setDataFim(evento.getDataFim());
		old.setDataInicio(evento.getDataInicio());
		old.setDescricao(evento.getDescricao());
		old.setTitulo(evento.getTitulo());
		old.appendTasks(validTasks);
		this.repository.save(old);
		this.notificationService.sendSchedulers(old.getTasks());

		return membrosOcupados;

	}

	private void bindMember(Task task) {
		Optional<Membro> membroOp = this.membroService.findOne(task.getMembroId());
		if (membroOp.isEmpty())
			return;
		Membro membro = membroOp.get();
		task.setStatus(TaskStatus.DEFAULT);
		task.setMembro(membro);
	}

	private void saveEvento(Evento evento) {
		LOGGER.info("Novas tarefas: ");
		LOGGER.info(evento.toString());
		evento.setCriador(this.authenticationService.getMembroLogged());
		this.eventoRepository.save(evento);
	}

	private List<Task> bindTasks(Evento evneto, List<Membro> membros) {
		return membros.stream().map(m -> {
			Task t = new Task();
			t.setMembro(m);
			t.setEvento(evneto);
			t.setStatus(TaskStatus.DEFAULT);
			return t;
		}).collect(Collectors.toList());
	}

}
