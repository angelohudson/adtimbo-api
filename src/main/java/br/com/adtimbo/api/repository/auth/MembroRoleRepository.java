package br.com.adtimbo.api.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adtimbo.api.model.auth.MembroRole;
import br.com.adtimbo.api.model.auth.MembroRoleId;

public interface MembroRoleRepository extends JpaRepository<MembroRole, MembroRoleId> {

}
