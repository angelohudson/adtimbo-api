package br.com.adtimbo.api.model.auth;

import br.com.adtimbo.api.model.core.Membro;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserSystem extends User {

	private static final long serialVersionUID = 1L;
	private Membro membro;

    public UserSystem(Membro membro, Collection<? extends GrantedAuthority> authorities) {
        super(membro.getEmail(), membro.getPassword(), authorities);
        this.membro = membro;
    }

    public Membro getMembro() {
        return membro;
    }
}
