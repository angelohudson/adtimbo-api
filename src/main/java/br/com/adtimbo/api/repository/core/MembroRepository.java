package br.com.adtimbo.api.repository.core;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.adtimbo.api.model.core.Membro;

public interface MembroRepository extends JpaRepository<Membro, Integer> {

	@Query(value = "SELECT m.*\n"
		+ "	from tb_membro m\n"
		+ "inner join tb_membro_ministerio mi\n"
		+ " on mi.membro_id = m.id\n"
		+ " and mi.ministerio_id = :ministerioId\n"
		+ "left JOIN tb_funcao f\n"
		+ "	on m.id = f.membro\n"
		+ " and f.ativo\n"
		+ "	and f.funcao_id = :tipo\n"
		+ "where f.id is null;", nativeQuery = true)
	List<Membro> findQuandoFuncaoNaoExiste(@Param("tipo") Integer id, @Param("ministerioId") Integer ministerioId);

	public Optional<Membro> findByCpf(String mail);

	List<Membro> findByMinisteriosId(Long ministerio);

	List<Membro> findByGruposId(Long grupoId);

	List<Membro> findByFuncoesFuncaoTituloId(Integer funcaoTituloId);

	@Query(value = "SELECT m.*\n"
		+ "	from tb_membro m\n"
		+ "inner join tb_membro_ministerio mi\n"
		+ " on mi.membro_id = m.id\n"
		+ " and mi.ministerio_id = :ministerioId\n"
		+ "left JOIN tb_membro_grupo f\n"
		+ "	on m.id = f.membro_id\n"
		+ "	and f.grupo_id = :id\n"
		+ "where f.grupo_id is null;", nativeQuery = true)
	List<Membro> findQuandoGrupoNaoEstaAssociado(@Param("id") Integer grupoId, @Param("ministerioId") Integer ministerioId);

	@Query(value = "SELECT m.*\n"
		+ "	from tb_membro m\n"
		+ "left JOIN tb_membro_ministerio f\n"
		+ "	on m.id = f.membro_id\n"
		+ "	and f.ministerio_id = :id\n"
		+ "where f.ministerio_id is null;", nativeQuery = true)
	List<Membro> findQuandoMinisterioNaoEstaAssociado(@Param("id") Integer ministerioiId);

	@Query(value = "SELECT m.*\r\n"
		+ "from tb_membro m\r\n"
		+ "inner join tb_membro_ministerio mi\n"
		+ " on mi.membro_id = m.id\n"
		+ " and mi.ministerio_id = :id\n"
		+ "left join tb_lideranca l\r\n"
		+ "	on m.id = l.membro_id\r\n"
		+ "	and l.ministerio_id = :id\r\n"
		+ "where l.id is null;", nativeQuery = true)
	List<Membro> findMembrosNaoAssociadoNaLiderancaDoMinisterio(@Param("id") Long ministerioId);

}
