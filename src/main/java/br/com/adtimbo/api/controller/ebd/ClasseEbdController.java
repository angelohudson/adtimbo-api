package br.com.adtimbo.api.controller.ebd;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.adtimbo.api.controller.ebd.dto.ClasseDto;
import br.com.adtimbo.api.controller.ebd.form.ClasseForm;
import br.com.adtimbo.api.model.ebd.core.Classe;
import br.com.adtimbo.api.service.ebd.ClasseService;

@RestController
@RequestMapping(value = "/ebd/classe", produces = "application/json;charset=UTF-8")
public class ClasseEbdController {

	@Autowired
	private ClasseService classeService;

	@PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO')")
    public ResponseEntity<ClasseDto> cadastra(@RequestBody ClasseForm form, UriComponentsBuilder uriBuilder) {
		Classe membro = form.converter();
		this.classeService.save(membro);
		URI uri = uriBuilder.path("/classe/{id}").buildAndExpand(membro.getId()).toUri();
		ClasseDto classeDto = new ClasseDto(membro);
		return ResponseEntity.created(uri).body(classeDto);
	}

	@PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO')")
    public ResponseEntity<ClasseDto> atualizar(@PathVariable Integer id, @RequestBody ClasseForm form) {
		Classe classe = form.converter();
		Optional<Classe> optional = this.classeService.findOne(id);
		if (optional.isPresent()) {
			classe.setId(id);
			this.classeService.update(classe);
			return ResponseEntity.ok(new ClasseDto(classe));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO')")
    public ResponseEntity<?> remover(@PathVariable Integer id) {
		Optional<Classe> optional = this.classeService.findOne(id);
		if (optional.isPresent()) {
			this.classeService.delete(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO')")
    public List<ClasseDto> lista(UriComponentsBuilder uriBuilder) {
		List<Classe> classeDto = this.classeService.findAll();
		return ClasseDto.converter(classeDto);
	}

	@GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO')")
    public ResponseEntity<ClasseDto> detalhar(@PathVariable Integer id) {
		Optional<Classe> optional = this.classeService.findOne(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new ClasseDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}

}
