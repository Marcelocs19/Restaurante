package br.com.restaurante.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.restaurante.modelo.Funcionario;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long>{

}
