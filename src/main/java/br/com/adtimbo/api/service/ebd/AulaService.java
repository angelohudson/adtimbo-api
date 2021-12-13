package br.com.adtimbo.api.service.ebd;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adtimbo.api.model.ebd.aula.Aula;
import br.com.adtimbo.api.repository.ebd.AulaRepository;
import br.com.adtimbo.api.service.auth.AuthenticationService;

@Service
public class AulaService {

	@Autowired
	private AulaRepository repository;
	@Autowired
	private AuthenticationService authenticationService;

	public List<Aula> findAll() {
		List<Integer> classesAutorizadas = authenticationService.getClassesAutorizadas();
		return this.repository.findByClasseIdIn(classesAutorizadas);
	}

	public Optional<Aula> findOne(Integer id) {
		List<Integer> classesAutorizadas = authenticationService.getClassesAutorizadas();
		return this.repository.findByIdAndClasseIdIn(id, classesAutorizadas);
	}

	@Transactional
	public void save(Aula aluno) {
		this.repository.save(aluno);
	}

	public void update(Aula aluno) {
		this.save(aluno);
	}
}
