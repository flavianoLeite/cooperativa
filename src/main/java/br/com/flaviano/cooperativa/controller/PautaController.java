package br.com.flaviano.cooperativa.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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

import br.com.flaviano.cooperativa.controller.dto.PautaDto;
import br.com.flaviano.cooperativa.controller.form.PautaForm;
import br.com.flaviano.cooperativa.modelo.Pauta;
import br.com.flaviano.cooperativa.repository.PautaRepository;

@RestController
@RequestMapping("/pautas")
public class PautaController {

	@Autowired
	private PautaRepository pautaRepository;

	@GetMapping
	@Cacheable( value = "listaDePautas")
	public Page<PautaDto> listas(@RequestParam(required = false) String titulo, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao){
		
		if( titulo == null) {
			Page<Pauta> pagePautas = pautaRepository.findAll(paginacao);
			return PautaDto.convert(pagePautas);			
		}
		Page<Pauta> pagePautas = pautaRepository.findByTitulo(titulo, paginacao);
		return PautaDto.convert(pagePautas);		
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDePautas", allEntries = true)
	public ResponseEntity<PautaDto> cadastrar(@RequestBody @Valid PautaForm form, UriComponentsBuilder uribuiBuilder) {		
		Pauta pauta = form.converter();
		pautaRepository.save(pauta);
		URI uri = uribuiBuilder.path("/pautas/{id}").buildAndExpand(pauta.getId()).toUri();
		return ResponseEntity.created(uri).body(new PautaDto(pauta));
	}
	
	@RequestMapping("/{id}")
	public ResponseEntity<PautaDto> detalhar(@PathVariable Long id) {
		Optional<Pauta> opt = pautaRepository.findById(id);
		if ( !opt.isPresent() ) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(new PautaDto(opt.get()));
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDePautas", allEntries = true)
	public ResponseEntity<PautaDto> atualizar(@PathVariable Long id, @RequestBody @Valid PautaForm form, UriComponentsBuilder uribuiBuilder) {
		Optional<Pauta> opt = pautaRepository.findById(id);
		if ( !opt.isPresent() ) {
			return ResponseEntity.notFound().build();
		}
		Pauta pauta = form.atualizar(id, pautaRepository);		
		return ResponseEntity.ok(new PautaDto(pauta));
	}
	
	
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDePautas", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Pauta> opt = pautaRepository.findById(id);
		if ( !opt.isPresent() ) {
			return ResponseEntity.notFound().build();
		}		
		pautaRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
