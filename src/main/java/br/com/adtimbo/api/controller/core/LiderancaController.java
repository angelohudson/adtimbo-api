package br.com.adtimbo.api.controller.core;

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
import br.com.adtimbo.api.controller.core.form.LiderancaForm;
import br.com.adtimbo.api.model.core.Lideranca;
import br.com.adtimbo.api.service.core.LiderancaService;

@RestController
@RequestMapping(value = "/lideranca", produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = "*")
public class LiderancaController {

	@Autowired
	private LiderancaService liderancaService;

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<LiderancaDto> cadastra(@RequestBody LiderancaForm form, UriComponentsBuilder uriBuilder) {
		if (this.liderancaService.associateMembro(form.getMembros(), form.getMinisterioId()))
			return ResponseEntity.ok().build();
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{lideranca-id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<LiderancaDto> remove(@PathVariable("lideranca-id") Long liderancaId,
			UriComponentsBuilder uriBuilder) {
		if (this.liderancaService.removeMembro(liderancaId))
			return ResponseEntity.ok().build();
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{ministerio-id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public List<LiderancaDto> lista(@PathVariable("ministerio-id") Long ministerioId, UriComponentsBuilder uriBuilder) {
		List<Lideranca> liderancas = this.liderancaService.findAll(ministerioId);
		return LiderancaDto.converter(liderancas);
	}

}
