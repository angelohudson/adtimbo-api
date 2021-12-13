package br.com.adtimbo.api.service.core;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import br.com.adtimbo.api.model.auth.MembroRole;
import br.com.adtimbo.api.model.auth.MembroRoleId;
import br.com.adtimbo.api.model.core.Funcao;
import br.com.adtimbo.api.model.core.FuncaoTitulo;
import br.com.adtimbo.api.model.core.Membro;
import br.com.adtimbo.api.model.core.Ministerio;
import br.com.adtimbo.api.repository.auth.MembroRoleRepository;
import br.com.adtimbo.api.repository.core.FuncaoTituloRepository;
import br.com.adtimbo.api.repository.ebd.FuncaoRepository;
import br.com.adtimbo.api.service.auth.AuthenticationService;
import br.com.adtimbo.api.service.ebd.AlunoService;
import br.com.adtimbo.api.service.ebd.FuncaoServiceAbstract;
import br.com.adtimbo.api.service.ebd.ProfessorService;
import br.com.adtimbo.api.service.ebd.SecretarioService;

@Service
public class FuncaoService {

	@Autowired
	private FuncaoTituloRepository funcaoTituloRepository;

	@Autowired
	private FuncaoRepository funcaoRepository;

	@Autowired
	private MinisterioService ministerioService;

	@Autowired
	private MembroService membroService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private MembroRoleRepository membroRoleRepository;

	public boolean save(FuncaoTitulo funcao) {
		Optional<Ministerio> ministerio = this.ministerioService.findOne(funcao.getIdMinisterio());
		if (ministerio.isEmpty())
			return false;

		funcao.setMinisterio(ministerio.get());

		this.funcaoTituloRepository.save(funcao);
		return true;
	}

	public List<FuncaoTitulo> findTituloByMinisterio(Long ministerioId) {
		List<Long> ministeriosAutorizados = this.authenticationService.getMinisteriosAutorizadosToChange();
		return this.funcaoTituloRepository.findByMinisterioIdAndMinisterioIdIn(ministerioId, ministeriosAutorizados);
	}

	public List<Funcao> findByMinisterio(Long ministerioId) {
		List<FuncaoTitulo> titulos = this.findTituloByMinisterio(ministerioId);
		return this.funcaoRepository.findByAtivoAndFuncaoTituloIdIn(true,
				titulos.stream().map(f -> f.getId()).collect(Collectors.toList()));
	}

	public void associa(List<Funcao> funcoes) {
		for (Funcao funcao : funcoes) {
			// Verificando se o membro existe
			Optional<Membro> membro = this.membroService.findOne(funcao.getIdMembro());
			if (membro.isEmpty())
				continue;

			// Verificando se a função existe
			Optional<FuncaoTitulo> funcaoTitulo = this.funcaoTituloRepository.findById(funcao.getIdFuncaoTitulo());
			if (funcaoTitulo.isEmpty())
				continue;

			// Criando o objeto funcao
			funcao.setFuncaoTitulo(funcaoTitulo.get());
			funcao.setMembro(membro.get());

			// Salvando a role
			MembroRole role = this.getRole(funcao);
			this.membroRoleRepository.save(role);

			// Salvando a funcao
			this.save(funcao);

			// Salvando a função especializado caso exista
			Class<? extends FuncaoServiceAbstract> funcaoServAbs = this.map.get(funcao.getTipo());
			if (funcaoServAbs != null) {
				FuncaoServiceAbstract adtionalService = context.getBean(funcaoServAbs);
				adtionalService.save(funcao);
			}
		}

	}

	private void save(Funcao funcao) {
		Optional<Funcao> op = this.funcaoRepository.findByAtivoAndFuncaoTituloIdAndMembroId(false,
				funcao.getIdFuncaoTitulo(), funcao.getIdMembro());
		if (op.isPresent()) {
			Funcao funcaoOld = op.get();
			funcaoOld.setAtivo(true);
			this.funcaoRepository.save(funcaoOld);
		} else {
			this.funcaoRepository.save(funcao);
		}
	}

	private MembroRole getRole(Funcao funcao) {
		MembroRole role = new MembroRole();
		MembroRoleId roleId = new MembroRoleId();
		roleId.setMembroId(funcao.getMembro().getId());
		roleId.setRoleId(funcao.getFuncaoTitulo().getRoleId().intValue());
		role.setId(roleId);
		return role;
	}

	// Funções com entidades próprias

	@Autowired
	private ApplicationContext context;

	private HashMap<String, Class<? extends FuncaoServiceAbstract>> map;

	public FuncaoService() {
		this.map = new HashMap<>();
		this.map.put("PROFESSOR", ProfessorService.class);
		this.map.put("SECRETARIO", SecretarioService.class);
		this.map.put("ALUNO", AlunoService.class);
	}

	public List<Funcao> findByTitulo(Integer tituloId) {
		return this.funcaoRepository.findByAtivoAndFuncaoTituloId(true, tituloId);
	}

	public Optional<Funcao> findOne(Integer funcaoId) {
		return this.funcaoRepository.findById(funcaoId);
	}

	public void remove(List<Funcao> funcoes) {
		for (Funcao funcao : funcoes) {
			// Deletando a Função
			funcao = this.funcaoRepository
					.findByAtivoAndFuncaoTituloIdAndMembroId(true, funcao.getIdFuncaoTitulo(), funcao.getIdMembro())
					.get();
			funcao.setAtivo(false);
			this.funcaoRepository.save(funcao);
			// Deletando os acessos
			MembroRoleId id = new MembroRoleId();
			id.setMembroId(funcao.getIdMembro());
			id.setRoleId(funcao.getFuncaoTitulo().getRoleId().intValue());
			MembroRole role = this.membroRoleRepository.findById(id).get();
			this.membroRoleRepository.delete(role);
		}
	}

}
