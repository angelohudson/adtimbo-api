package br.com.adtimbo.api.service.ebd;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adtimbo.api.model.auth.MembroRole;
import br.com.adtimbo.api.model.auth.MembroRoleId;
import br.com.adtimbo.api.model.core.Funcao;
import br.com.adtimbo.api.model.ebd.funcao.Professor;
import br.com.adtimbo.api.repository.auth.MembroRoleRepository;
import br.com.adtimbo.api.repository.ebd.ProfessorRepository;

@Service
public class ProfessorService extends FuncaoServiceAbstract {

	@Autowired
	private ProfessorRepository repository;

	@Autowired
	private MembroRoleRepository membroRoleRepository;

	public List<Professor> findAll() {
		return this.repository.findAll();
	}

	public Optional<Professor> findOne(Integer id) {
		return this.repository.findById(id);
	}

	@Transactional
	public void save(Professor professor) {
		this.membroRoleRepository.save(this.getRole(professor));
		this.repository.save(professor);
	}

	public void update(Professor professor) {
		this.save(professor);
	}

	public List<Professor> findByClasse(Integer classeId) {
		return this.repository.findByClasseId(classeId);
	}

	private MembroRole getRole(Professor professor) {
		MembroRole role = new MembroRole();
		MembroRoleId roleId = new MembroRoleId();
		roleId.setMembroId(professor.getFuncao().getMembro().getId());
		roleId.setRoleId(3);
		role.setId(roleId);
		return role;
	}

	@Override
	public void save(Funcao funcao) {
		Professor professor = new Professor();
		professor.setFuncao(funcao);
		this.save(professor);
	}

	@Override
	public void remove(Funcao funcao) {
	}
}
