package br.com.restaurante.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.restaurante.modelo.Estado;
import br.com.restaurante.modelo.Restaurante;

@Repository
public interface RestauranteRepositorio extends JpaRepository<Restaurante, Long>{
	
	List<Restaurante> findByEstado(Estado estado);
	
	List<Restaurante> findByDataVitoria(LocalDate data);
	
	List<Restaurante> findAllByOrderByNumeroVotosDesc();
	
	List<Restaurante> findAllByOrderByDataVitoriaDesc();
	
}
