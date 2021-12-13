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

import br.com.adtimbo.api.controller.ebd.dto.AlunoDto;
import br.com.adtimbo.api.controller.ebd.form.AlunoForm;
import br.com.adtimbo.api.model.ebd.funcao.Aluno;
import br.com.adtimbo.api.service.ebd.AlunoService;
import br.com.adtimbo.api.service.ebd.ClasseService;

@RestController
@RequestMapping(value = "/ebd/aluno", produces = "application/json;charset=UTF-8")
public class AlunoEbdController {

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private ClasseService classeService;

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO')")
	public ResponseEntity<AlunoDto> atualizar(@PathVariable Integer id, @RequestBody AlunoForm form) {
		Optional<Aluno> optional = this.alunoService.findOne(id);
		if (optional.isPresent()) {
			Aluno aluno = form.converter(this.classeService, optional.get());
			aluno.setId(id);
			this.alunoService.update(aluno);
			return ResponseEntity.ok(new AlunoDto(aluno));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO')")
	public List<AlunoDto> lista(UriComponentsBuilder uriBuilder) {
		List<Aluno> alunoDto = this.alunoService.findAll();
		return AlunoDto.converter(alunoDto);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SECRETARIO')")
	public ResponseEntity<AlunoDto> detalhar(@PathVariable Integer id) {
		Optional<Aluno> optional = this.alunoService.findOne(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new AlunoDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}

}
