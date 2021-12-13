package br.com.adtimbo.api.service.core;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adtimbo.api.model.core.Membro;
import br.com.adtimbo.api.model.core.Ministerio;
import br.com.adtimbo.api.repository.core.MinisterioRepository;
import br.com.adtimbo.api.service.auth.AuthenticationService;

@Service
public class MinisterioService {
	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private MinisterioRepository ministerioRepository;

	@Autowired
	private MembroService membroService;

	public Optional<Ministerio> findOneToView(Long id) {
		Optional<Ministerio> op = Optional.empty();
		List<Ministerio> ministeriosAutorizados = this.authenticationService.getMinisteriosAutorizadosToView();
		List<Ministerio> ministeriosFiltrados = ministeriosAutorizados.stream().filter(m -> m.getId() == id)
				.collect(Collectors.toList());
		if (ministeriosFiltrados.isEmpty())
			return op;
		op = Optional.of(ministeriosFiltrados.get(0));
		return op;
	}

	public List<Ministerio> findAllToView() {
		return this.authenticationService.getMinisteriosAutorizadosToView();
	}

	public Optional<Ministerio> findOne(Long id) {
		List<Long> ministeriosAutorizados = this.authenticationService.getMinisteriosAutorizadosToChange();
		return this.ministerioRepository.findByIdAndIdIn(id, ministeriosAutorizados);
	}

	public List<Ministerio> findAll() {
		List<Long> ministeriosAutorizados = this.authenticationService.getMinisteriosAutorizadosToChange();
		return this.ministerioRepository.findByIdIn(ministeriosAutorizados);
	}

	public void save(Ministerio ministerio) {
		this.ministerioRepository.save(ministerio);
	}

	public boolean associaMembros(List<Integer> ids, Long ministerioId) {
		Optional<Ministerio> ministerio = this.findOne(ministerioId);

		if (ministerio.isEmpty())
			return false;

		for (Integer membroId : ids) {
			Optional<Membro> membroOp = membroService.findOne(membroId);
			if (membroOp.isEmpty())
				return false;

			Membro membro = membroOp.get();
			membro.addMinisterio(ministerio.get());

			this.membroService.save(membro);
		}

		return true;
	}

	public boolean desassociaMembro(Integer membroId, Long ministerioId) {
		Optional<Ministerio> ministerio = this.findOne(ministerioId);

		if (ministerio.isEmpty())
			return false;

		Optional<Membro> membroOp = membroService.findOne(membroId);
		if (membroOp.isEmpty())
			return false;

		Membro membro = membroOp.get();
		membro.removeMinisterio(ministerio.get());

		this.membroService.save(membro);

		return true;
	}

}
