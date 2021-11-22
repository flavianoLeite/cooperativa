package br.com.flaviano.cooperativa.controller.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import br.com.flaviano.cooperativa.modelo.Pauta;
import br.com.flaviano.cooperativa.modelo.StatusPauta;

public class PautaDto {
	private Long id;
	private String titulo;
	private String descricao;
	private LocalDateTime dataCriacao = LocalDateTime.now();
	private StatusPauta status = StatusPauta.NAO_VOTADA;
	
	public PautaDto(Pauta pauta) {		
		this.id = pauta.getId();
		this.titulo = pauta.getTitulo();
		this.descricao = pauta.getDescricao();
		this.dataCriacao = pauta.getDataCriacao();
		this.status = pauta.getStatus();
	}

	public Long getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	public StatusPauta getStatus() {
		return status;
	}

	public static Page<PautaDto> convert(Page<Pauta> pagePautas) {
		return pagePautas.map(PautaDto::new);
	}
}
