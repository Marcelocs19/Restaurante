package br.com.restaurante.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.restaurante.modelo.Restaurante;

public interface RestauranteRepositorio extends JpaRepository<Restaurante, Long>{

}
