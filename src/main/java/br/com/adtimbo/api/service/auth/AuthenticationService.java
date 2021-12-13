package br.com.adtimbo.api.service.auth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.adtimbo.api.model.auth.Role;
import br.com.adtimbo.api.model.core.Membro;
import br.com.adtimbo.api.model.core.Ministerio;
import br.com.adtimbo.api.model.ebd.core.Classe;
import br.com.adtimbo.api.repository.core.MembroRepository;
import br.com.adtimbo.api.repository.core.MinisterioRepository;
import br.com.adtimbo.api.repository.ebd.ClasseRepository;

@Service
public class AuthenticationService {

	@Autowired
	private ClasseRepository repository;

	@Autowired
	private MinisterioRepository ministerioRepository;

	@Autowired
	private MembroRepository membroRepository;

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public String getLoggedCpf() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public List<Integer> getClassesAutorizadas() {
		Membro membro = getMembroLogged();
		List<Classe> classes = new ArrayList<Classe>();
		if (this.isAdmin(membro)) {
			classes = this.repository.findAll();
		} else {
			classes = repository.findClassesByMembroId(membro.getId());
		}
		return classes.stream().map(c -> c.getId()).collect(Collectors.toList());
	}

	/*
	 * Ministérios liberados para visualização. Esses ministérios estarão
	 * disponíveis para o membro logado que possui alguma função relacionada ao
	 * ministério. Servirá apenas para que o mesmo veja quais ministérios ele está
	 * associado.
	 * 
	 */
	public List<Ministerio> getMinisteriosAutorizadosToView() {
		Membro membro = getMembroLogged();
		if (this.isAdmin(membro)) {
			List<Ministerio> ministerios = this.ministerioRepository.findAll();
			return ministerios;
		} else {
			HashSet<Ministerio> collect = new HashSet<>();
			
			collect.addAll(membro.getFuncoes().stream()
					.filter(f -> f.getAtivo())
					.map(f -> f.getFuncaoTitulo().getMinisterio())
					.collect(Collectors.toList()));
			
			collect.addAll(membro.getLiderancas().stream()
					.map(m -> m.getMinisterio())
					.collect(Collectors.toList()));
			
			return new ArrayList<Ministerio>(collect);
		}
	}

	/*
	 * Ministérios liberados para alterações Esses ministérios estarão disponíveis
	 * para que os membros possam realizar alteraçõeos. Desde a criação de grupos,
	 * funções, atividades como as respectivas associações.
	 * 
	 */
	public List<Long> getMinisteriosAutorizadosToChange() {
		Membro membro = getMembroLogged();
		if (this.isAdmin(membro)) {
			List<Ministerio> ministerios = this.ministerioRepository.findAll();
			return ministerios.stream().map(m -> m.getId()).collect(Collectors.toList());
		} else {
			return membro.getLiderancas().stream().map(m -> m.getMinisterio().getId()).collect(Collectors.toList());
		}
	}

	public Membro getMembroLogged() {
		String cpf = this.getAuthentication().getName();
		Optional<Membro> optional = membroRepository.findByCpf(cpf);
		Membro membro = optional.get();
		return membro;
	}

	private boolean isAdmin(Membro membro) {
		List<Role> roleAdmin = membro.getRoles().stream().filter(r -> {
			boolean equals = r.getId().equals(2l);
			return equals;
		}).collect(Collectors.toList());

		return roleAdmin.size() > 0;
	}

}