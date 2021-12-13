package br.com.adtimbo.api.controller.core.form;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.web.multipart.MultipartFile;

import br.com.adtimbo.api.model.core.Membro;

public class MembroForm {
	private String nome;
	private String email;
	private String nascimento;
	private String telefone;
	private String cpf;
	private EnderecoForm endereco;
	private String password;
	private MultipartFile file;

	public Membro converter() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Membro membro = new Membro();
		membro.setEmail(email);
		membro.setNascimento(LocalDate.parse(this.nascimento, formatter));
		membro.setNome(nome);
		membro.setTelefone(telefone);
		if (cpf != null)
			membro.setCpf(cpf);
		if (password != null && !password.trim().isEmpty())
			membro.setPassword(password);
		membro.setEndereco(endereco.converter());

		return membro;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EnderecoForm getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoForm endereco) {
		this.endereco = endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
