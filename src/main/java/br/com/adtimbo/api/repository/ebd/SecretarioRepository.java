package br.com.adtimbo.api.repository.ebd;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adtimbo.api.model.ebd.funcao.Secretario;

public interface SecretarioRepository extends JpaRepository<Secretario, Integer> {
}
