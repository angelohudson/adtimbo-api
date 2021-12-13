package br.com.adtimbo.api.repository.ebd;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adtimbo.api.model.ebd.funcao.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

	public List<Aluno> findByClasseIdIn(List<Integer> classesAutorizadas);

	public Optional<Aluno> findByIdAndClasseIdIn(Integer id, List<Integer> classesAutorizadas);

}
