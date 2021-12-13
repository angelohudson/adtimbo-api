package br.com.adtimbo.api.repository.core;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adtimbo.api.model.core.Ministerio;

public interface MinisterioRepository extends JpaRepository<Ministerio, Long> {

	List<Ministerio> findByIdIn(List<Long> ministeriosAutorizados);

	Optional<Ministerio> findByIdAndIdIn(Long id, List<Long> ministeriosAutorizados);
}
