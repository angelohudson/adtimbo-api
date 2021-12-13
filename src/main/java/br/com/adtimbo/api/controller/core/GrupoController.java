package br.com.adtimbo.api.controller.core;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.adtimbo.api.controller.core.dto.GrupoDto;
import br.com.adtimbo.api.controller.core.form.GrupoForm;
import br.com.adtimbo.api.controller.core.form.GrupoMembroForm;
import br.com.adtimbo.api.model.core.Grupo;
import br.com.adtimbo.api.service.core.GrupoService;

@RestController
@RequestMapping(value = "/grupo", produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = "*")
public class GrupoController {

	@Autowired
	private GrupoService grupoService;

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<GrupoDto> cadastra(@RequestBody GrupoForm form, UriComponentsBuilder uriBuilder) {
		Grupo grupo = form.converter();
		if (this.grupoService.save(grupo)) {
			URI uri = uriBuilder.path("/grupo/{id}").buildAndExpand(grupo.getId()).toUri();
			return ResponseEntity.created(uri).body(new GrupoDto(grupo));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{ministerio-id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public List<GrupoDto> lista(UriComponentsBuilder uriBuilder, @PathVariable("ministerio-id") Long ministerioId) {
		List<Grupo> grupo = this.grupoService.findByMinisterio(ministerioId);
		return GrupoDto.converter(grupo);
	}

	@PostMapping("/associa-membro")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<Object> associaMembros(@RequestBody GrupoMembroForm form, UriComponentsBuilder uriBuilder) {
		if (this.grupoService.associaMembros(form.getMembros(), form.getGrupoId())) {
			return ResponseEntity.created(uriBuilder.path("/grupo/{id}").buildAndExpand(form.getGrupoId()).toUri())
					.build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/remove-membro")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<Object> removeMembros(@RequestBody GrupoMembroForm form, UriComponentsBuilder uriBuilder) {
		if (this.grupoService.removeMembros(form.getMembros(), form.getGrupoId())) {
			return ResponseEntity.created(uriBuilder.path("/grupo/{id}").buildAndExpand(form.getGrupoId()).toUri())
					.build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
