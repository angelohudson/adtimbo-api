package br.com.adtimbo.api.controller.core;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.adtimbo.api.controller.core.dto.MembroDto;
import br.com.adtimbo.api.controller.core.form.MembroForm;
import br.com.adtimbo.api.model.core.Membro;
import br.com.adtimbo.api.service.core.MembroService;

@RestController
@RequestMapping(value = "/membro", produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = "*")
public class MembroController {

	@Autowired
	private MembroService membroService;

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA') or hasRole('ROLE_SECRETARIO')")
	public ResponseEntity<MembroDto> cadastra(@RequestBody MembroForm form, UriComponentsBuilder uriBuilder) {
		Membro membro = form.converter();
		this.membroService.save(membro);
		URI uri = uriBuilder.path("/membro/{id}").buildAndExpand(membro.getId()).toUri();
		MembroDto membroDto = new MembroDto(membro);
		return ResponseEntity.created(uri).body(membroDto);
	}

	@PostMapping("/photo/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA') or hasRole('ROLE_SECRETARIO')")
	public ResponseEntity<String> uploadFile(@RequestParam("photo") MultipartFile file,
			@PathVariable("id") Integer id) {
		try {
			this.membroService.uploadFile(file, id);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(file.getOriginalFilename());
		}
		return ResponseEntity.ok(file.getOriginalFilename());
	}

	@GetMapping(value = "/photo/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA') or hasRole('ROLE_SECRETARIO')")
	public ResponseEntity<byte[]> getImage(@PathVariable("id") Integer id) throws IOException {
		byte[] bytes = this.membroService.getPhotoById(id);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
	}

	@GetMapping(value = "/photo", produces = MediaType.IMAGE_JPEG_VALUE)
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<byte[]> getImageByAuth(Authentication authentication) throws IOException {
		Membro membro = this.membroService.findOne(authentication.getName()).get();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(membro.getImagem());
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA') or hasRole('ROLE_SECRETARIO')")
	public ResponseEntity<MembroDto> atualizar(@PathVariable Integer id, @RequestBody MembroForm form) {
		Membro membro = form.converter();
		if (this.membroService.update(membro, id)) {
			return ResponseEntity.ok(new MembroDto(membro, id));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA') or hasRole('ROLE_SECRETARIO')")
	public ResponseEntity<?> remover(@PathVariable Integer id) {
		Optional<Membro> optional = this.membroService.findOne(id);
		if (optional.isPresent()) {
			this.membroService.delete(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA') or hasRole('ROLE_SECRETARIO')")
	public List<MembroDto> lista() {
		List<Membro> membrosDto = this.membroService.findAll();
		return MembroDto.converter(membrosDto);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA') or hasRole('ROLE_SECRETARIO')")
	public ResponseEntity<MembroDto> detalhar(@PathVariable Integer id) {
		Optional<Membro> optional = this.membroService.findOne(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new MembroDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/not-associate/{ministerio-id}/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA') or hasRole('ROLE_SECRETARIO')")
	public List<MembroDto> notAssociateMembro(@PathVariable Integer id,
			@PathVariable("ministerio-id") Integer ministerioId) {
		List<Membro> membrosDto = this.membroService.findByFuncaoNotIn(id, ministerioId);
		return MembroDto.converter(membrosDto);
	}

	@GetMapping("/grupo-not-associate/{ministerio-id}/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA') or hasRole('ROLE_SECRETARIO')")
	public List<MembroDto> grupoNotAssociateMembro(@PathVariable Integer id,
			@PathVariable("ministerio-id") Integer ministerioId) {
		List<Membro> membrosDto = this.membroService.findByGrupoNotIn(id, ministerioId);
		return MembroDto.converter(membrosDto);
	}

	@GetMapping("/ministerio-not-associate/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA') or hasRole('ROLE_SECRETARIO')")
	public List<MembroDto> ministerioNotAssociateMembro(@PathVariable Integer id) {
		List<Membro> membrosDto = this.membroService.findByMinisterioNotIn(id);
		return MembroDto.converter(membrosDto);
	}

	@GetMapping("/lideranca-not-associate/{ministerio-id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA')")
	public List<MembroDto> membroNotAssociate(@PathVariable("ministerio-id") Long ministerioId,
			UriComponentsBuilder uriBuilder) {
		List<Membro> membros = this.membroService.findByLiderancaNotIn(ministerioId);
		return MembroDto.converter(membros);
	}

	@GetMapping("/by-ministerio/{ministerio}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA') or hasRole('ROLE_SECRETARIO')")
	public List<MembroDto> membrosByMinisterio(@PathVariable("ministerio") Long ministerio) {
		List<Membro> membrosDto = this.membroService.findByMinisterio(ministerio);
		return MembroDto.converter(membrosDto);
	}

	@GetMapping("/by-grupo/{grupo}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIDERANCA') or hasRole('ROLE_SECRETARIO')")
	public List<MembroDto> membrosByGrupo(@PathVariable("grupo") Long grupo) {
		List<Membro> membrosDto = this.membroService.findByGrupo(grupo);
		return MembroDto.converter(membrosDto);
	}

}
