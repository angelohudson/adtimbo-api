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

import br.com.adtimbo.api.controller.core.dto.LiderancaDto;
import br.com.adtimbo.api.controller.core.dto.MinisterioDto;
import br.com.adtimbo.api.controller.core.form.MinisterioForm;
import br.com.adtimbo.api.controller.core.form.MinisterioMembroForm;
import br.com.adtimbo.api.model.core.Ministerio;
import br.com.adtimbo.api.service.core.MinisterioService;

@RestController
@RequestMapping(value = "/ministerio", produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = "*")
public class MinisterioController {

	@Autowired
	private MinisterioService ministerioService;

	@GetMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA') or hasRole('ROLE_SECRETARIO')")
	public List<MinisterioDto> lista(UriComponentsBuilder uriBuilder) {
		List<Ministerio> ministerios = this.ministerioService.findAllToView();
		return MinisterioDto.converter(ministerios);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<MinisterioDto> cadastra(@RequestBody MinisterioForm form, UriComponentsBuilder uriBuilder) {
		Ministerio ministerio = form.converter();
		this.ministerioService.save(ministerio);
		URI uri = uriBuilder.path("/ministerio/{id}").buildAndExpand(ministerio.getId()).toUri();
		MinisterioDto ministerioDto = new MinisterioDto(ministerio);
		return ResponseEntity.created(uri).body(ministerioDto);
	}

	@PostMapping("/associa-membro")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<String> associaMembros(@RequestBody MinisterioMembroForm form,
			UriComponentsBuilder uriBuilder) {
		if (this.ministerioService.associaMembros(form.getMembros(), form.getMinisterioId())) {
			return ResponseEntity.ok().body("");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/remove-membro/{ministerio-id}/{membro-id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<LiderancaDto> desassociaMembro(@PathVariable("ministerio-id") Long ministerioId,
			@PathVariable("membro-id") Integer membroId, UriComponentsBuilder uriBuilder) {
		if (this.ministerioService.desassociaMembro(membroId, ministerioId))
			return ResponseEntity.ok().build();
		return ResponseEntity.notFound().build();
	}

}
