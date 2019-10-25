package br.com.restaurante.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.restaurante.modelo.Votacao;

@Repository
public interface VotacaoRepositorio extends JpaRepository<Votacao, Long>{

	Votacao findByFuncionario(Long id);
		
	List<Votacao> findAllByOrderByDataVotoDesc();
	
	Votacao findByFuncionarioAndRestaurante(Long idFuncionario,Long IdRestaurante);
}
