package br.com.adtimbo.api.service.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.adtimbo.api.model.task.Task;
import br.com.adtimbo.api.model.task.TaskStatus;
import br.com.adtimbo.api.repository.task.TaskRepository;
import br.com.adtimbo.api.service.auth.AuthenticationService;

@Service
public class TaskService {

	private static final List<TaskStatus> STATUS_FINZALIZADO = Arrays
			.asList(new TaskStatus[] { TaskStatus.DELETADO, TaskStatus.CONCLUIDA });

	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	private TaskRepository taskRepository;

	final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

	public List<Task> findAll() {
		return this.taskRepository.findByMembroCpfAndStatusNotIn(authenticationService.getLoggedCpf(),
				STATUS_FINZALIZADO);

	}

	public Optional<Task> findOne(Long id) {
		return this.taskRepository.findByMembroCpfAndIdAndStatusNotIn(authenticationService.getLoggedCpf(), id,
				STATUS_FINZALIZADO);
	}

	public Optional<Task> confirmTask(Long id) {
		Optional<Task> op = this.findOne(id);
		if (op.isEmpty())
			return op;

		Task task = op.get();

		task.setStatus(TaskStatus.CONFIRMADO);

		this.taskRepository.save(task);

		return op;
	}

	public Optional<Task> cancelaTask(Long id, String justificativa) {
		Optional<Task> op = this.findOne(id);
		if (op.isEmpty())
			return op;

		Task task = op.get();

		task.setJustificativa(justificativa);
		task.setStatus(TaskStatus.DELETADO);

		this.taskRepository.save(task);

		return op;
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void concluiTaskAutomaticamente() {
		LOGGER.info("Processando tasks do dia");
		LocalDateTime endDate = LocalDate.now().minusDays(1).atTime(23, 59);
		List<Task> tasks = this.taskRepository.findByEventoDataFimLessThanAndStatusNotIn(endDate, STATUS_FINZALIZADO);
		tasks.stream().forEach(t -> t.setStatus(TaskStatus.CONCLUIDA));
		this.saveAll(tasks);
	}

	public void saveAll(List<Task> task) {
		this.taskRepository.saveAll(task);
	}

	public List<Task> findByMinisterioAndPeriodo(Long ministerioId, String beginDateString, String endDateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneOffset.UTC);
		LocalDateTime beginDate = LocalDateTime.parse(beginDateString, formatter);
		LocalDateTime endDate = LocalDateTime.parse(endDateString, formatter);
		return this.taskRepository.findByEventoMinisterioIdAndEventoDataInicioBetween(ministerioId, beginDate, endDate);
	}

	public List<Task> findByPeriodo(String beginDateString, String endDateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneOffset.UTC);
		LocalDateTime beginDate = LocalDateTime.parse(beginDateString, formatter);
		LocalDateTime endDate = LocalDateTime.parse(endDateString, formatter);
		return this.taskRepository.findByEventoDataInicioBetweenAndMembroCpf(beginDate, endDate,
				authenticationService.getLoggedCpf());
	}
}
