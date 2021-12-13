package br.com.adtimbo.api.controller.core.dto;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.model.auth.Role;
import br.com.adtimbo.api.model.core.Funcao;
import br.com.adtimbo.api.model.core.Grupo;
import br.com.adtimbo.api.model.core.Lideranca;
import br.com.adtimbo.api.model.core.Membro;
import br.com.adtimbo.api.model.core.Ministerio;

public class MembroDto {
	private Integer id;
	private String nome;
	private String email;
	private String nascimento;
	private String telefone;
	private List<MembroFuncaoDto> funcoes;
	private List<MembroLiderancaDto> liderancas;
	private List<String> grupos;
	private List<String> ministerios;
	private List<String> roles;
	private EnderecoDto endereco;

	public MembroDto(Membro membro) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneOffset.UTC);
		this.id = membro.getId();
		this.nome = membro.getNome();
		this.email = membro.getEmail();
		this.nascimento = formatter.format(membro.getNascimento());
		this.telefone = membro.getTelefone();
		if (membro.getEndereco() != null)
			this.endereco = new EnderecoDto(membro.getEndereco());
		if (membro.getFuncoes() != null)
			this.funcoes = membro.getFuncoes().stream().filter(f -> f.getAtivo()).map(MembroFuncaoDto::new)
					.collect(Collectors.toList());
		if (membro.getGrupos() != null)
			this.grupos = membro.getGrupos().stream().map(Grupo::getTitulo).collect(Collectors.toList());
		if (membro.getMinisterios() != null)
			this.ministerios = membro.getMinisterios().stream().map(Ministerio::getTitulo).collect(Collectors.toList());
		if (membro.getLiderancas() != null)
			this.setLiderancas(
					membro.getLiderancas().stream().map(MembroLiderancaDto::new).collect(Collectors.toList()));
		if (membro.getRoles() != null)
			this.setRoles(membro.getRoles().stream().map(Role::getDescription).collect(Collectors.toList()));

	}

	public MembroDto(Membro membro, Integer id) {
		this(membro);
		this.id = id;
	}

	public static List<MembroDto> converter(List<Membro> membrosDto) {
		return membrosDto.stream().map(MembroDto::new).collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public EnderecoDto getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDto endereco) {
		this.endereco = endereco;
	}

	public List<MembroFuncaoDto> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<MembroFuncaoDto> funcoes) {
		this.funcoes = funcoes;
	}

	public List<String> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<String> grupos) {
		this.grupos = grupos;
	}

	public List<String> getMinisterios() {
		return ministerios;
	}

	public void setMinisterios(List<String> ministerios) {
		this.ministerios = ministerios;
	}

	public List<MembroLiderancaDto> getLiderancas() {
		return liderancas;
	}

	public void setLiderancas(List<MembroLiderancaDto> liderancas) {
		this.liderancas = liderancas;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}

class MembroLiderancaDto {
	MinisterioDto ministerioDto;
	private Long id;

	public MembroLiderancaDto(Lideranca lideranca) {
		this.id = lideranca.getId();
		this.ministerioDto = new MinisterioDto(lideranca.getMinisterio());
	}

	public static List<LiderancaDto> converter(List<Lideranca> liderancas) {
		return liderancas.stream().map(LiderancaDto::new).collect(Collectors.toList());
	}

	public MinisterioDto getMinisterioDto() {
		return ministerioDto;
	}

	public void setMinisterioDto(MinisterioDto ministerioDto) {
		this.ministerioDto = ministerioDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

class MembroFuncaoDto {
	private Integer id;
	private String tipo;
	private Long ministerioId;

	public MembroFuncaoDto(Funcao funcao) {
		this.id = funcao.getId();
		this.tipo = funcao.getTipo();
		this.setMinisterioId(funcao.getFuncaoTitulo().getMinisterio().getId());
	}

	public static List<FuncaoDto> converter(List<Funcao> funcaoDto) {
		return funcaoDto.stream().map(FuncaoDto::new).collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getMinisterioId() {
		return ministerioId;
	}

	public void setMinisterioId(Long ministerioId) {
		this.ministerioId = ministerioId;
	}

}
