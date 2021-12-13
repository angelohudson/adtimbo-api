package br.com.adtimbo.api.model.auth;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_membro_roles")
public class MembroRole {

	@EmbeddedId
	private MembroRoleId id;

	public MembroRoleId getId() {
		return id;
	}

	public void setId(MembroRoleId id) {
		this.id = id;
	}

}

