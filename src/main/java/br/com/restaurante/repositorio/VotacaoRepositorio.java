package br.com.restaurante.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.restaurante.modelo.Votacao;

public interface VotacaoRepositorio extends JpaRepository<Votacao, Long>{

}
