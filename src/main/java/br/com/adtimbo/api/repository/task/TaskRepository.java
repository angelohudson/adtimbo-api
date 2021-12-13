package br.com.adtimbo.api.repository.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.adtimbo.api.model.task.Task;
import br.com.adtimbo.api.model.task.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByMembroCpfAndStatusNotIn(String email, List<TaskStatus> status);

	Optional<Task> findByMembroCpfAndIdAndStatusNotIn(String email, Long id, List<TaskStatus> status);

	List<Task> findByEventoDataFimLessThanAndStatusNotIn(LocalDateTime endDate, List<TaskStatus> status);

	List<Task> findByEventoMinisterioIdAndEventoDataInicioBetween(Long id, LocalDateTime beginDate, LocalDateTime endDates);

	@Query(value = "SELECT * from tb_task t\r\n"
			+ "inner join tb_evento e on e.id = t.evento_id\r\n"
			+ "where membro_id = :membro_id\r\n"
			+ "and funcao_escala is not null\r\n"
			+ "and (data_inicio BETWEEN :begin_date and :end_date\r\n"
			+ "or data_fim BETWEEN :begin_date and :end_date) limit 1;", nativeQuery = true)
	Optional<Task> findMembrosOcupados(@Param("membro_id") Integer id, @Param("begin_date") LocalDateTime beginDate,
			@Param("end_date") LocalDateTime endDates);

	List<Task> findByEventoDataInicioBetweenAndMembroCpf(LocalDateTime beginDate, LocalDateTime endDate, String cpf);

}
