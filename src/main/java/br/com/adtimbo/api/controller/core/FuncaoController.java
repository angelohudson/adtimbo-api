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

import br.com.adtimbo.api.controller.core.dto.FuncaoDto;
import br.com.adtimbo.api.controller.core.dto.FuncaoTituloDto;
import br.com.adtimbo.api.controller.core.form.FuncaoForm;
import br.com.adtimbo.api.controller.core.form.FuncaoTituloForm;
import br.com.adtimbo.api.model.core.Funcao;
import br.com.adtimbo.api.model.core.FuncaoTitulo;
import br.com.adtimbo.api.service.core.FuncaoService;

@RestController
@RequestMapping(value = "/funcao", produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = "*")
public class FuncaoController {

	@Autowired
	private FuncaoService funcaoService;

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<FuncaoTituloDto> cadastra(@RequestBody FuncaoTituloForm form,
			UriComponentsBuilder uriBuilder) {
		FuncaoTitulo funcao = form.converter();
		if (this.funcaoService.save(funcao)) {
			URI uri = uriBuilder.path("/funcao/{id}").buildAndExpand(funcao.getId()).toUri();
			return ResponseEntity.created(uri).body(new FuncaoTituloDto(funcao));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/associa-membro")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<Integer> assiciaMembro(@RequestBody FuncaoForm form, UriComponentsBuilder uriBuilder) {
		List<Funcao> funcoes = form.converter();
		this.funcaoService.associa(funcoes);
		URI uri = uriBuilder.path("/funcao/{id}").buildAndExpand(form.getFuncaoTituloId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/remove-membro")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public ResponseEntity<Integer> removeMembro(@RequestBody FuncaoForm form, UriComponentsBuilder uriBuilder) {
		List<Funcao> funcoes = form.converter();
		this.funcaoService.remove(funcoes);
		URI uri = uriBuilder.path("/funcao/{id}").buildAndExpand(form.getFuncaoTituloId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping("/titulo/{ministerio-id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public List<FuncaoTituloDto> listaTitulosPorMinisterio(UriComponentsBuilder uriBuilder,
			@PathVariable("ministerio-id") Long ministerioId) {
		List<FuncaoTitulo> grupo = this.funcaoService.findTituloByMinisterio(ministerioId);
		return FuncaoTituloDto.converter(grupo);
	}

	@GetMapping("/{titulo-id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public List<FuncaoDto> listaPorMinisterio(UriComponentsBuilder uriBuilder,
			@PathVariable("titulo-id") Integer tituloId) {
		List<Funcao> funcoes = this.funcaoService.findByTitulo(tituloId);
		return FuncaoDto.converter(funcoes);
	}

}
