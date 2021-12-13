package br.com.adtimbo.api.service.ebd;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adtimbo.api.model.core.Funcao;
import br.com.adtimbo.api.model.ebd.funcao.Aluno;
import br.com.adtimbo.api.repository.ebd.AlunoRepository;
import br.com.adtimbo.api.service.auth.AuthenticationService;

@Service
public class AlunoService extends FuncaoServiceAbstract {

	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	private AlunoRepository repository;

	public List<Aluno> findAll() {
		List<Integer> classesAutorizadas = authenticationService.getClassesAutorizadas();
		return this.repository.findByClasseIdIn(classesAutorizadas);
	}

	public Optional<Aluno> findOne(Integer id) {
		List<Integer> classesAutorizadas = authenticationService.getClassesAutorizadas();
		return this.repository.findByIdAndClasseIdIn(id, classesAutorizadas);
	}

	@Transactional
	public void save(Aluno aluno) {
		this.repository.save(aluno);
	}

	public void update(Aluno aluno) {
		this.save(aluno);
	}

	@Override
	public void save(Funcao funcao) {
		Aluno aluno = new Aluno();
		aluno.setFuncao(funcao);
		aluno.setClasse(null);
		this.save(aluno);
	}

	@Override
	public void remove(Funcao funcao) {
	}
}
