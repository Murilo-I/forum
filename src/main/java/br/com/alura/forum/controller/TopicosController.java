package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.dto.DetalheDto;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.form.AtualizaForm;
import br.com.alura.forum.form.TopicoForm;
import br.com.alura.forum.models.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository tr;

	@Autowired
	private CursoRepository cr;
	
	@GetMapping
	@Cacheable(value = "ListaTopicos")
	public Page<TopicoDto> list(@RequestParam(required = false) String curso,
			@PageableDefault() Pageable pageable) {
				
		if(curso == null) {
			Page<Topico> topicos = tr.findAll(pageable);
			return TopicoDto.convert(topicos);
		} else {
			Page<Topico> topicos = tr.findByCursoNome(curso, pageable);
			return TopicoDto.convert(topicos);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "ListaTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form,
			UriComponentsBuilder uriBild) {
		
		Topico topico = form.convert(cr); 
		tr.save(topico);
		
		URI uri = uriBild.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalheDto> detalhe(@PathVariable Long id) {
		Optional<Topico> topico = tr.findById(id);
		
		if(topico.isPresent())
			return ResponseEntity.ok(new DetalheDto(topico.get()));
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@PutMapping("/{id}")
	@CacheEvict(value = "ListaTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, 
			@RequestBody @Valid AtualizaForm form) {
		
		Optional<Topico> otopic = tr.findById(id);
		
		if(otopic.isPresent()) {
			Topico topico = form.update(id, tr);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		 
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	@CacheEvict(value = "ListaTopicos", allEntries = true)
	public ResponseEntity<?> remove(@PathVariable Long id) {
		Optional<Topico> otopic = tr.findById(id);
		
		if(otopic.isPresent()) {
			tr.deleteById(id);
			return ResponseEntity.ok().build(); 
		}	
		
		return ResponseEntity.notFound().build();
	}
}
