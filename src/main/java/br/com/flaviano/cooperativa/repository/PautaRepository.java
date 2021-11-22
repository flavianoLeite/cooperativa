package br.com.flaviano.cooperativa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.flaviano.cooperativa.modelo.Pauta;

public interface PautaRepository extends JpaRepository<Pauta, Long> {

	Page<Pauta> findByTitulo(String titulo, Pageable paginacao);

}
