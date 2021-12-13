package br.com.adtimbo.api.service.auth;

import br.com.adtimbo.api.model.core.Membro;
import br.com.adtimbo.api.repository.core.MembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

	@Autowired
	MembroRepository membroRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String cpf = authentication.getName();
		String password = authentication.getCredentials().toString();

		Optional<Membro> membro = membroRepository.findByCpf(cpf);

		if (membro.get() != null && membro.get().getPassword().equals(password)) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			membro.get().getRoles()
					.forEach((role -> authorities.add(new SimpleGrantedAuthority(role.getDescription()))));
			return new UsernamePasswordAuthenticationToken(cpf, password, authorities);
		} else {
			throw new BadCredentialsException("Falha na autenticação do membro " + membro.get().getEmail());
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
