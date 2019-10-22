package br.com.restaurante.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.restaurante.modelo.Funcionario;

@Repository
public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long>{

}
