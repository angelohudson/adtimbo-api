package br.com.adtimbo.api.repository.core;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adtimbo.api.model.core.FuncaoTitulo;

public interface FuncaoTituloRepository extends JpaRepository<FuncaoTitulo, Integer> {

	List<FuncaoTitulo> findByMinisterioIdIn(List<Long> ministeriosAutorizados);

	List<FuncaoTitulo> findByMinisterioIdAndMinisterioIdIn(Long ministerioId, List<Long> ministeriosAutorizados);
}
