package br.com.adtimbo.api.model.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MembroRoleId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "roles_id")
	private Integer roleId;

	@Column(name = "membro_id")
	private Integer membroId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMembroId() {
		return membroId;
	}

	public void setMembroId(Integer membroId) {
		this.membroId = membroId;
	}

}