package br.com.adtimbo.api.service.ebd;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adtimbo.api.model.ebd.core.Classe;
import br.com.adtimbo.api.repository.ebd.ClasseRepository;
import br.com.adtimbo.api.service.auth.AuthenticationService;

@Service
public class ClasseService {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private ClasseRepository classeRepository;

	public List<Classe> findAll() {
		return this.classeRepository.findByIdIn(authenticationService.getClassesAutorizadas());
	}

	public Optional<Classe> findOne(Integer id) {
		return this.classeRepository.findByIdAndIdIn(id, authenticationService.getClassesAutorizadas());
	}

	@Transactional
	public void save(Classe classe) {
		this.classeRepository.save(classe);
	}

	public void update(Classe classe) {
		this.save(classe);
	}

	@Transactional
	public void delete(Integer id) {
		this.classeRepository.deleteById(id);
	}

}
