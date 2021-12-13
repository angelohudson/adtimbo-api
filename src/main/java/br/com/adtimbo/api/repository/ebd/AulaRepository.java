package br.com.adtimbo.api.repository.ebd;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adtimbo.api.model.ebd.aula.Aula;

public interface AulaRepository extends JpaRepository<Aula, Integer> {

	List<Aula> findByClasseIdIn(List<Integer> classesAutorizadas);

	Optional<Aula> findByIdAndClasseIdIn(Integer id, List<Integer> classesAutorizadas);

}
