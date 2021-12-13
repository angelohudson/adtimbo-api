package br.com.adtimbo.api.repository.core;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adtimbo.api.model.core.Lideranca;

public interface LiderancaRepository extends JpaRepository<Lideranca, Long> {

	List<Lideranca> findByMembroId(Integer id);

	List<Lideranca> findByMinisterioId(Long id);

}
