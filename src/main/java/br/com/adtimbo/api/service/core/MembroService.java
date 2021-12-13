package br.com.adtimbo.api.service.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.adtimbo.api.model.core.Endereco;
import br.com.adtimbo.api.model.core.Membro;
import br.com.adtimbo.api.model.core.Ministerio;
import br.com.adtimbo.api.repository.core.MembroRepository;

@Service
public class MembroService {

	@Autowired
	private MembroRepository membroRepository;

	@Autowired
	private MinisterioService ministerioService;

	public List<Membro> findAll() {
		return this.membroRepository.findAll();
	}

	public Optional<Membro> findOne(Integer id) {
		return this.membroRepository.findById(id);
	}

	public Optional<Membro> findOne(String cpf) {
		return this.membroRepository.findByCpf(cpf);
	}

	@Transactional
	public void save(Membro membro) {
		this.membroRepository.save(membro);
	}

	public boolean update(Membro membro, Integer id) {
		Optional<Membro> optional = this.findOne(id);
		if (optional.isEmpty())
			return false;

		Membro old = optional.get();
		Endereco endereco = membro.getEndereco();
		Endereco enderecoOld = old.getEndereco();

		enderecoOld.setBairro(endereco.getBairro());
		enderecoOld.setCep(endereco.getCep());
		enderecoOld.setComplemento(endereco.getComplemento());
		enderecoOld.setLocalidade(endereco.getLocalidade());
		enderecoOld.setLogradouro(endereco.getLogradouro());
		enderecoOld.setUf(endereco.getUf());
		enderecoOld.setNumero(endereco.getNumero());

		old.setEmail(membro.getEmail());
		old.setNascimento(membro.getNascimento());
		old.setNome(membro.getNome());
		old.setTelefone(membro.getTelefone());
		old.setEndereco(enderecoOld);

		if (membro.getPassword() != null && !membro.getPassword().trim().isEmpty())
			old.setPassword(membro.getPassword());

		this.save(old);
		return true;
	}

	public boolean uploadFile(MultipartFile file, Integer id) throws IOException {
		byte[] data = file.getBytes();
		Optional<Membro> op = this.findOne(id);
		if (op.isEmpty())
			return false;
		Membro membro = op.get();
		membro.setImagem(data);
		this.save(membro);
		return true;
	}

	public byte[] getPhotoById(Integer id) throws IOException {
		Optional<Membro> membro = this.findOne(id);
		if (membro.isEmpty())
			return null;
		return membro.get().getImagem();
	}

	@Transactional
	public void delete(Integer id) {
		this.membroRepository.deleteById(id);
	}

	public List<Membro> findByFuncaoNotIn(Integer id, Integer ministerioId) {
		return this.membroRepository.findQuandoFuncaoNaoExiste(id, ministerioId);
	}

	public List<Membro> findByMinisterio(Long ministerioId) {
		Optional<Ministerio> ministerio = this.ministerioService.findOne(ministerioId);

		if (ministerio.isEmpty())
			return new ArrayList<Membro>();

		return ministerio.get().getMembros();
	}

	public List<Membro> findByGrupoNotIn(Integer id, Integer ministerioId) {
		return this.membroRepository.findQuandoGrupoNaoEstaAssociado(id, ministerioId);
	}

	public List<Membro> findByMinisterioNotIn(Integer id) {
		return this.membroRepository.findQuandoMinisterioNaoEstaAssociado(id);
	}

	public List<Membro> findByLiderancaNotIn(Long ministerioId) {
		return this.membroRepository.findMembrosNaoAssociadoNaLiderancaDoMinisterio(ministerioId);
	}

	public List<Membro> findByGrupo(Long grupo) {
		return this.membroRepository.findByGruposId(grupo);
	}

	public List<Membro> findByFuncaoTitulo(Integer funcaoTitulo) {
		return this.membroRepository.findByFuncoesFuncaoTituloId(funcaoTitulo);
	}

	public boolean saveDeviceToken(String token, String cpf) {
		Optional<Membro> op = this.membroRepository.findByCpf(cpf);
		if (op.isEmpty())
			return false;
		Membro membro = op.get();
		membro.setDeviceToken(token);
		this.membroRepository.save(membro);
		return true;
	}
}
