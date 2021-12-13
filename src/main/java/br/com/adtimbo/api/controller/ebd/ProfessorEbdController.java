package br.com.adtimbo.api.controller.ebd;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.adtimbo.api.controller.ebd.dto.ProfessorDto;
import br.com.adtimbo.api.controller.ebd.form.ProfessorForm;
import br.com.adtimbo.api.model.ebd.funcao.Professor;
import br.com.adtimbo.api.service.ebd.ClasseService;
import br.com.adtimbo.api.service.ebd.ProfessorService;

@RestController
@RequestMapping(value = "/ebd/professor", produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = "*")
public class ProfessorEbdController {

	@Autowired
	private ProfessorService professorService;

	@Autowired
	private ClasseService classeService;

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO')")
	public ResponseEntity<ProfessorDto> atualizar(@PathVariable Integer id, @RequestBody ProfessorForm form) {
		Optional<Professor> optional = this.professorService.findOne(id);
		if (optional.isPresent()) {
			Professor professor = form.converter(this.classeService, optional.get());
			professor.setId(id);
			this.professorService.update(professor);
			return ResponseEntity.ok(new ProfessorDto(professor));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO')")
	public List<ProfessorDto> lista(UriComponentsBuilder uriBuilder) {
		List<Professor> professorDto = this.professorService.findAll();
		return ProfessorDto.converter(professorDto);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO')")
	public ResponseEntity<ProfessorDto> detalhar(@PathVariable Integer id) {
		Optional<Professor> optional = this.professorService.findOne(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new ProfessorDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/classe/{classeId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO')")
	public List<ProfessorDto> buscaPorClasse(@PathVariable Integer classeId) {
		List<Professor> professorDto = this.professorService.findByClasse(classeId);
		return ProfessorDto.converter(professorDto);
	}

}
