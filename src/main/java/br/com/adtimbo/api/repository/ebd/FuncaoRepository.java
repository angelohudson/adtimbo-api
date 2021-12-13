package br.com.adtimbo.api.repository.ebd;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adtimbo.api.model.core.Funcao;

public interface FuncaoRepository extends JpaRepository<Funcao, Integer> {

	List<Funcao> findByAtivoAndFuncaoTituloIdIn(Boolean ativo, List<Integer> collect);

	List<Funcao> findByAtivoAndFuncaoTituloId(Boolean ativo, Integer tituloId);

	Optional<Funcao> findByAtivoAndFuncaoTituloIdAndMembroId(Boolean ativo, Integer tituloId, Integer membroId);

	List<Funcao> findByAtivoAndMembroId(Boolean ativo, Integer membroId);

}
