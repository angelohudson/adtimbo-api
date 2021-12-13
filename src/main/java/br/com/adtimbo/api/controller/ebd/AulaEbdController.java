package br.com.adtimbo.api.controller.ebd;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.adtimbo.api.controller.ebd.dto.AulaDto;
import br.com.adtimbo.api.controller.ebd.form.AulaForm;
import br.com.adtimbo.api.model.ebd.aula.Aula;
import br.com.adtimbo.api.service.ebd.AlunoService;
import br.com.adtimbo.api.service.ebd.AulaService;
import br.com.adtimbo.api.service.ebd.ClasseService;
import br.com.adtimbo.api.service.ebd.ProfessorService;

@RestController
@RequestMapping(value = "/ebd/aula", produces = "application/json;charset=UTF-8")
public class AulaEbdController {

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private AulaService aulaService;

	@Autowired
	private ProfessorService professorService;

	@Autowired
	private ClasseService classeService;

	@PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO')")
    public ResponseEntity<AulaDto> cadastra(@RequestBody AulaForm form, UriComponentsBuilder uriBuilder) {
		Aula aula = form.converter(this.classeService, this.professorService, this.alunoService);
		this.aulaService.save(aula);
		URI uri = uriBuilder.path("/aula/{id}").buildAndExpand(aula.getId()).toUri();
		AulaDto aulaDto = new AulaDto(aula);
		return ResponseEntity.created(uri).body(aulaDto);
	}

	@PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO') or hasRole('ROLE_PROFESSOR')")
    public ResponseEntity<AulaDto> atualizar(@PathVariable Integer id, @RequestBody AulaForm form) {
		Optional<Aula> optional = this.aulaService.findOne(id);
		if (optional.isPresent()) {
			Aula aula = form.converter(optional.get());
			aula.setId(id);
			this.aulaService.update(aula);
			return ResponseEntity.ok(new AulaDto(aula));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO') or hasRole('ROLE_PROFESSOR')")
    public List<AulaDto> lista(UriComponentsBuilder uriBuilder) {
		List<Aula> aulaDto = this.aulaService.findAll();
		return AulaDto.converter(aulaDto);
	}

	@GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO') or hasRole('ROLE_PROFESSOR')")
    public ResponseEntity<AulaDto> detalhar(@PathVariable Integer id) {
		Optional<Aula> optional = this.aulaService.findOne(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new AulaDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}

}
