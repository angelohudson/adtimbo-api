package br.com.adtimbo.api.repository.ebd;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.adtimbo.api.model.ebd.funcao.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
	
	@Query(value = "SELECT p.*\n"
			+ "from tb_professor p\n"
			+ "left join tb_professor_classe pc \n"
			+ "on pc.professor = p.id \n"
			+ "where pc.classe = :classeId ;", nativeQuery = true)
	List<Professor> findByClasseId(@Param("classeId") Integer classeId);
	
}
