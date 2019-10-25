package br.com.restaurante.configuracao;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.restaurante.modelo.Estado;
import br.com.restaurante.modelo.Funcionario;
import br.com.restaurante.modelo.Restaurante;
import br.com.restaurante.modelo.Votacao;
import br.com.restaurante.repositorio.FuncionarioRepositorio;
import br.com.restaurante.repositorio.RestauranteRepositorio;
import br.com.restaurante.repositorio.VotacaoRepositorio;

@Configuration
public class Instanciacao implements CommandLineRunner {

	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	
	@Autowired
	private RestauranteRepositorio restauranteRepositorio;
	
	@Autowired
	private VotacaoRepositorio votacaoRepositorio;

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
		
		LocalDate dataTeste = LocalDate.of(2019, 10, 24);
		Restaurante restaurante1 = new Restaurante("Silva Lanches", Estado.INDISPONIVEL,dataTeste,0);
		Restaurante restaurante2 = new Restaurante("Mazá", Estado.DISPONIVEL, 0);
		Restaurante restaurante3 = new Restaurante("Espaço 32", Estado.DISPONIVEL, 0);
		Restaurante restaurante4 = new Restaurante("Severo Garage", Estado.DISPONIVEL, 0);
		Restaurante restaurante5 = new Restaurante("Restaurante Vitória", Estado.DISPONIVEL, 0);
		Restaurante restaurante6 = new Restaurante("Pé de Manga", Estado.DISPONIVEL, 0);		
		
		restauranteRepositorio.saveAll(Arrays.asList(restaurante1,restaurante2,restaurante3,restaurante4,restaurante5,restaurante6));

		votacaoRepositorio.deleteAll();
		
		Votacao votacao1 = new Votacao(dataTeste,funcionario1, restaurante1);
		Votacao votacao2 = new Votacao(dataTeste,funcionario2, restaurante1);
		Votacao votacao3 = new Votacao(dataTeste,funcionario3, restaurante1);
		Votacao votacao4 = new Votacao(dataTeste,funcionario4, restaurante2);
		Votacao votacao5 = new Votacao(dataTeste,funcionario5, restaurante2);
		
		votacaoRepositorio.saveAll(Arrays.asList(votacao1,votacao2,votacao3,votacao4,votacao5));
		
	}
	
}
