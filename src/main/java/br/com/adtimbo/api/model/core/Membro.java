package br.com.adtimbo.api.model.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import br.com.adtimbo.api.model.auth.Role;
import lombok.Data;

@Data
@Entity
@EnableAutoConfiguration
@Table(name = "tb_membro")
public class Membro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "email")
	private String email;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "password")
	private String password;

	@Column(name = "device_token")
	private String deviceToken;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	@Column(name = "telefone")
	private String telefone;

	@Column(name = "nome")
	private String nome;

	@Column(name = "nascimento")
	private LocalDate nascimento;

	@Lob
	@Column(name = "imagem", columnDefinition = "mediumblob")
	private byte[] imagem;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_id", referencedColumnName = "id")
	private Endereco endereco;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tb_membro_ministerio", joinColumns = { @JoinColumn(name = "membro_id") }, inverseJoinColumns = {
			@JoinColumn(name = "ministerio_id") })
	private List<Ministerio> ministerios = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tb_membro_grupo", joinColumns = { @JoinColumn(name = "membro_id") }, inverseJoinColumns = {
			@JoinColumn(name = "grupo_id") })
	private List<Grupo> grupos = new ArrayList<>();

	@OneToMany(mappedBy = "membro")
	private List<Funcao> funcoes;

	@OneToMany(mappedBy = "membro")
	private List<Lideranca> liderancas;

	public void addGrupo(Grupo grupos) {
		this.grupos.add(grupos);
	}

	public void addMinisterio(Ministerio ministerio) {
		this.ministerios.add(ministerio);
	}

	public void removeMinisterio(Ministerio ministerio) {
		this.ministerios.remove(ministerio);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public List<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<Funcao> funcoes) {
		this.funcoes = funcoes;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Ministerio> getMinisterios() {
		return ministerios;
	}

	public void setMinisterios(List<Ministerio> ministerios) {
		this.ministerios = ministerios;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public String getDeviceToken() {
		return this.deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public void removeGrupo(Grupo grupo) {
		this.grupos.remove(grupo);
	}

	@Override
	public String toString() {
		return "Membro [id=" + id + ", email=" + email + ", cpf=" + cpf + ", password=" + password + ", deviceToken="
				+ deviceToken + ", roles=" + roles + ", telefone=" + telefone + ", nome=" + nome + ", nascimento="
				+ nascimento + ", endereco=" + endereco + ", ministerios=" + ministerios + ", grupos=" + grupos
				+ ", funcoes=" + funcoes + ", liderancas=" + liderancas + "]";
	}


}