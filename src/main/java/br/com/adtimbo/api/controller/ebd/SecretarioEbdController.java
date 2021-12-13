package br.com.adtimbo.api.controller.ebd;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.adtimbo.api.controller.ebd.dto.SecretarioDto;
import br.com.adtimbo.api.controller.ebd.form.SecretarioForm;
import br.com.adtimbo.api.model.ebd.funcao.Secretario;
import br.com.adtimbo.api.service.ebd.ClasseService;
import br.com.adtimbo.api.service.ebd.SecretarioService;

@RestController
@RequestMapping(value = "/ebd/secretario", produces = "application/json;charset=UTF-8")
public class SecretarioEbdController {

	@Autowired
	private SecretarioService secretarioService;

	@Autowired
	private ClasseService classeService;

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<SecretarioDto> atualizar(@PathVariable Integer id, @RequestBody SecretarioForm form) {
		Optional<Secretario> optional = this.secretarioService.findOne(id);
		if (optional.isPresent()) {
			Secretario secretario = form.converter(this.classeService, optional.get());
			secretario.setId(id);
			this.secretarioService.update(secretario);
			return ResponseEntity.ok(new SecretarioDto(secretario));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<SecretarioDto> lista(UriComponentsBuilder uriBuilder) {
		List<Secretario> professorDto = this.secretarioService.findAll();
		return SecretarioDto.converter(professorDto);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<SecretarioDto> detalhar(@PathVariable Integer id) {
		Optional<Secretario> optional = this.secretarioService.findOne(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new SecretarioDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}

}
