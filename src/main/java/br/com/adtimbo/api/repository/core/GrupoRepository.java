package br.com.adtimbo.api.repository.core;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adtimbo.api.model.core.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	List<Grupo> findByMinisterioIdIn(List<Long> ministeriosAutorizados);

	Optional<Grupo> findByIdAndMinisterioIdIn(Long id, List<Long> ministeriosAutorizados);

	List<Grupo> findByIdIn(List<Grupo> idGrupos);

	List<Grupo> findByMinisterioIdAndMinisterioIdIn(Long ministerioId, List<Long> ministeriosAutorizados);

}
