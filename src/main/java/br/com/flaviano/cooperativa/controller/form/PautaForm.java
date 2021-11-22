package br.com.flaviano.cooperativa.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.flaviano.cooperativa.modelo.Pauta;
import br.com.flaviano.cooperativa.repository.PautaRepository;

public class PautaForm {

	@NotNull @NotBlank
	private String titulo;
	
	@NotNull @NotBlank
	private String descricao;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Pauta converter() { 
		return new Pauta(titulo, descricao);
	}
	
	public Pauta atualizar(Long id, PautaRepository pautaRepository) {
		Optional<Pauta> opt = pautaRepository.findById(id);			
		Pauta pauta = opt.get();
		pauta.setTitulo(titulo);
		pauta.setDescricao(descricao);
		return pauta;
	}
}
