package br.com.adtimbo.api.service.core;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adtimbo.api.model.core.Grupo;
import br.com.adtimbo.api.model.core.Membro;
import br.com.adtimbo.api.model.core.Ministerio;
import br.com.adtimbo.api.repository.core.GrupoRepository;
import br.com.adtimbo.api.service.auth.AuthenticationService;

@Service
public class GrupoService {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private MinisterioService ministerioService;

	@Autowired
	private MembroService membroService;

	@Autowired
	private GrupoRepository grupoRepository;

	public boolean save(Grupo grupo) {
		Optional<Ministerio> ministerio = this.ministerioService.findOne(grupo.getIdMinisterio());
		if (ministerio.isEmpty())
			return false;

		this.grupoRepository.save(grupo);
		return true;
	}

	public List<Grupo> findByMinisterio(Long ministerioId) {
		List<Long> ministeriosAutorizados = this.authenticationService.getMinisteriosAutorizadosToChange();
		return this.grupoRepository.findByMinisterioIdAndMinisterioIdIn(ministerioId, ministeriosAutorizados);
	}

	public Optional<Grupo> findOne(Long id) {
		List<Long> ministeriosAutorizados = this.authenticationService.getMinisteriosAutorizadosToChange();
		return this.grupoRepository.findByIdAndMinisterioIdIn(id, ministeriosAutorizados);
	}

	public boolean associaMembros(List<Integer> ids, Long grupoId) {
		Optional<Grupo> grupo = this.findOne(grupoId);
		if (grupo.isEmpty())
			return false;
		for (Integer membroId : ids) {
			Optional<Membro> membroOp = membroService.findOne(membroId);
			if (membroOp.isEmpty())
				continue;

			Membro membro = membroOp.get();
			membro.addGrupo(grupo.get());

			this.membroService.save(membro);
		}
		return true;
	}

	public boolean removeMembros(List<Integer> membros, Long grupoId) {
		Optional<Grupo> op = this.findOne(grupoId);
		if (op.isEmpty())
			return false;
		for (Integer membroId : membros) {
			Optional<Membro> membroOp = membroService.findOne(membroId);
			if (membroOp.isEmpty())
				continue;

			Membro membro = membroOp.get();
			membro.removeGrupo(op.get());
			this.membroService.save(membro);
		}
		return true;
	}

}
