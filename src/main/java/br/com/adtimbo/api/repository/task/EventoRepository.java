package br.com.adtimbo.api.repository.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adtimbo.api.model.task.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

	List<Evento> findByMinisterioIdAndDataInicioBetween(Long id, LocalDateTime beginDate, LocalDateTime endDates);

}
