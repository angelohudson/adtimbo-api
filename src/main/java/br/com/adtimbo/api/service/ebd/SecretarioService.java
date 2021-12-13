package br.com.adtimbo.api.service.ebd;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adtimbo.api.model.auth.MembroRole;
import br.com.adtimbo.api.model.auth.MembroRoleId;
import br.com.adtimbo.api.model.core.Funcao;
import br.com.adtimbo.api.model.ebd.funcao.Secretario;
import br.com.adtimbo.api.repository.auth.MembroRoleRepository;
import br.com.adtimbo.api.repository.ebd.SecretarioRepository;

@Service
public class SecretarioService extends FuncaoServiceAbstract {

	@Autowired
	private SecretarioRepository repository;

	@Autowired
	private MembroRoleRepository membroRoleRepository;

	public List<Secretario> findAll() {
		return this.repository.findAll();
	}

	public Optional<Secretario> findOne(Integer id) {
		return this.repository.findById(id);
	}

	@Transactional
	public void save(Secretario secretario) {
		MembroRole role = getRole(secretario);
		this.membroRoleRepository.save(role);
		this.repository.save(secretario);
	}

	public void update(Secretario secretario) {
		this.save(secretario);
	}

	private MembroRole getRole(Secretario secretario) {
		MembroRole role = new MembroRole();
		MembroRoleId roleId = new MembroRoleId();
		roleId.setMembroId(secretario.getFuncao().getMembro().getId());
		roleId.setRoleId(1);
		role.setId(roleId);
		return role;
	}

	@Override
	public void save(Funcao funcao) {
		Secretario secretario = new Secretario();
		secretario.setFuncao(funcao);
		this.save(secretario);
	}

	@Override
	public void remove(Funcao funcao) {
	}
}
