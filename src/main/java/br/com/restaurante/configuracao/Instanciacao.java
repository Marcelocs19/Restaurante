package br.com.restaurante.configuracao;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.restaurante.modelo.Estado;
import br.com.restaurante.modelo.Funcionario;
import br.com.restaurante.modelo.Restaurante;
import br.com.restaurante.repositorio.FuncionarioRepositorio;
import br.com.restaurante.repositorio.RestauranteRepositorio;

@Configuration
public class Instanciacao implements CommandLineRunner {

	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	
	@Autowired
	private RestauranteRepositorio restauranteRepositorio;

	@Override
	public void run(String... args) throws Exception {

		funcionarioRepositorio.deleteAll();

		Funcionario funcionario1 = new Funcionario("João","joao@email.com",false);
		Funcionario funcionario2 = new Funcionario("Pedro","pedro@email.com",false);
		Funcionario funcionario3 = new Funcionario("Maria","maria@email.com",false);
		Funcionario funcionario4 = new Funcionario("Ana","ana@email.com",false);
		Funcionario funcionario5 = new Funcionario("Paula","paula@email.com",false);
		
		funcionarioRepositorio.saveAll(Arrays.asList(funcionario1,funcionario2,funcionario3,funcionario4,funcionario5));
		
		
		restauranteRepositorio.deleteAll();
		
		Restaurante restaurante1 = new Restaurante("Silva Lanches", Estado.DISPONIVEL, 0);
		Restaurante restaurante2 = new Restaurante("Mazá", Estado.DISPONIVEL, 0);
		Restaurante restaurante3 = new Restaurante("Espaço 32", Estado.DISPONIVEL, 0);
		Restaurante restaurante4 = new Restaurante("Espaço 32", Estado.DISPONIVEL, 0);
		Restaurante restaurante5 = new Restaurante("Severo Garage", Estado.DISPONIVEL, 0);
		Restaurante restaurante6 = new Restaurante("Restaurante Vitória", Estado.DISPONIVEL, 0);
		Restaurante restaurante7 = new Restaurante("Pé de Manga", Estado.DISPONIVEL, 0);
		
		
		restauranteRepositorio.saveAll(Arrays.asList(restaurante1,restaurante2,restaurante3,restaurante4,restaurante5,restaurante6,restaurante7));

	}
	
}
