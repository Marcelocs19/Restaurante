package br.com.restaurante.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.restaurante.modelo.Votacao;

@Repository
public interface VotacaoRepositorio extends JpaRepository<Votacao, Long>{

}
