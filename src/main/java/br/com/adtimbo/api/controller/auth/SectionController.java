package br.com.adtimbo.api.controller.auth;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.adtimbo.api.controller.auth.form.DeviceTokenForm;
import br.com.adtimbo.api.controller.core.dto.MembroDto;
import br.com.adtimbo.api.service.core.MembroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/auth", produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = "*")
@Api(value = "Detalhes da sessão")
public class SectionController {

	@Autowired
	MembroService membroService;

	@RequestMapping(value = "/username", method = RequestMethod.GET)
	@ApiOperation("Retorna o nome do usuário logado")
	public String currentUserName(Authentication authentication) {

		return authentication.getName();

	}

	@RequestMapping(value = "/authorities", method = RequestMethod.GET)
	@ApiOperation("Retorna as regras do usuário")
	public Collection<? extends GrantedAuthority> currentAuthorities(Authentication authentication) {

		return authentication.getAuthorities();

	}

	@RequestMapping(value = "/me", method = RequestMethod.GET)
	@ApiOperation("Retorna as detalhes do usuário")
	public MembroDto me(Authentication authentication) {
		return new MembroDto(this.membroService.findOne(authentication.getName()).get());
	}

	@RequestMapping(value = "/basic", method = RequestMethod.GET)
	@ApiOperation("Retorna o status de logado")
	public HttpStatus basicLogin() {

		return HttpStatus.OK;

	}

	@RequestMapping(value = "/device-token", method = RequestMethod.POST)
	@ApiOperation("Salva o token do dispositivo logado")
	public HttpStatus saveDeviceToken(Authentication authentication, @RequestBody DeviceTokenForm form) {
		this.membroService.saveDeviceToken(form.getToken(), authentication.getName());
		return HttpStatus.OK;
	}

}