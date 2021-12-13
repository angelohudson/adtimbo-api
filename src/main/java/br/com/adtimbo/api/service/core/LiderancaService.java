package br.com.adtimbo.api.service.core;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adtimbo.api.model.auth.MembroRole;
import br.com.adtimbo.api.model.auth.MembroRoleId;
import br.com.adtimbo.api.model.core.Lideranca;
import br.com.adtimbo.api.model.core.Membro;
import br.com.adtimbo.api.model.core.Ministerio;
import br.com.adtimbo.api.repository.auth.MembroRoleRepository;
import br.com.adtimbo.api.repository.core.LiderancaRepository;

@Service
public class LiderancaService {

	@Autowired
	private LiderancaRepository liderancaRepository;

	@Autowired
	private MinisterioService ministerioService;

	@Autowired
	private MembroService membroService;

	@Autowired
	private MembroRoleRepository membroRoleRepository;

	public void save(Lideranca lideranca) {
		this.membroRoleRepository.save(this.getRole(lideranca.getMembro()));
		this.liderancaRepository.save(lideranca);
	}

	public List<Lideranca> findAll(Long ministerioId) {
		return this.liderancaRepository.findByMinisterioId(ministerioId);
	}

	private MembroRole getRole(Membro membro) {
		MembroRole role = new MembroRole();
		MembroRoleId roleId = new MembroRoleId();
		roleId.setMembroId(membro.getId());
		roleId.setRoleId(4);
		role.setId(roleId);
		return role;
	}

	public boolean associateMembro(List<Integer> membros, Long ministerioId) {
		Optional<Ministerio> ministerioOp = ministerioService.findOne(ministerioId);
		if (ministerioOp.isEmpty())
			return false;
		Ministerio ministerio = ministerioOp.get();
		for (Integer membroId : membros) {
			Optional<Membro> membroOp = this.membroService.findOne(membroId);
			if (membroOp.isEmpty())
				continue;
			Membro membro = membroOp.get();
			this.save(new Lideranca(membro, ministerio));
		}
		return true;
	}

	public boolean removeMembro(Long liderancaId) {
		Optional<Lideranca> lideranca = this.liderancaRepository.findById(liderancaId);
		if (lideranca.isEmpty())
			return false;
		this.liderancaRepository.deleteById(liderancaId);
		return true;
	}
}
