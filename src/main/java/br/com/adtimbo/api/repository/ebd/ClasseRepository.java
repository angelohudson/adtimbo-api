package br.com.adtimbo.api.repository.ebd;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.adtimbo.api.model.ebd.core.Classe;

public interface ClasseRepository extends JpaRepository<Classe, Integer> {

	@Query(value = "select c.* from tb_classe c\n"
			+ "left join tb_secretario_classe sc on c.id = sc.classe \n"
			+ "left join tb_professor_classe pc on c.id = pc.classe \n"
			+ "left join tb_secretario s on sc.secretario = s.id\n"
			+ "left join tb_professor p on pc.professor = p.id\n"
			+ "inner join tb_funcao f on p.funcao = f.id or s.funcao = f.id\n"
			+ "inner join tb_membro m on f.membro = m.id\n"
			+ "where m.id = :membroId ;", nativeQuery = true)
	public List<Classe> findClassesByMembroId(@Param("membroId") Integer id);

	public List<Classe> findByIdIn(List<Integer> classesAutorizadas);

	public Optional<Classe> findByIdAndIdIn(Integer id, List<Integer> classesAutorizadas);

}
