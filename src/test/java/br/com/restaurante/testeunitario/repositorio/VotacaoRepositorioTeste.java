package br.com.restaurante.testeunitario.repositorio;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.restaurante.RestauranteApplication;
import br.com.restaurante.modelo.Estado;
import br.com.restaurante.modelo.Funcionario;
import br.com.restaurante.modelo.Restaurante;
import br.com.restaurante.modelo.Votacao;
import br.com.restaurante.repositorio.FuncionarioRepositorio;
import br.com.restaurante.repositorio.RestauranteRepositorio;
import br.com.restaurante.repositorio.VotacaoRepositorio;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestauranteApplication.class)
public class VotacaoRepositorioTeste {
	

	@Autowired
	private RestauranteRepositorio restauranteRepositorio;
	
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	
	@Autowired
	private VotacaoRepositorio votacaoRepositorio;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Restaurante restauranteNovo;
	private Restaurante restauranteNovo2;
	
	private Funcionario funcionarioNovo;
	private Funcionario funcionarioNovo2;
	
	private Votacao votacao;
	private Votacao votacao2;
	
	private LocalDate dataVoto;
	
	@Before
	public void antes() {
		this.restauranteRepositorio.deleteAll();
		
		restauranteNovo = new Restaurante();
		restauranteNovo.setNome("Churrascaria");
		restauranteNovo.setEstado(Estado.DISPONIVEL);
		restauranteNovo.setNumeroVotos(0);
		
		this.restauranteRepositorio.saveAndFlush(restauranteNovo);
		
		restauranteNovo2 = new Restaurante();
		restauranteNovo2.setNome("Galeteria");
		restauranteNovo2.setEstado(Estado.DISPONIVEL);
		restauranteNovo2.setNumeroVotos(0);
		
		this.restauranteRepositorio.saveAndFlush(restauranteNovo2);
				
		this.funcionarioRepositorio.deleteAll();
		
		funcionarioNovo = new Funcionario();
		funcionarioNovo.setNome("Wiliam");
		funcionarioNovo.setEmail("wiliam@email.com.br");
		funcionarioNovo.setVoto(false);	
		
		this.funcionarioRepositorio.saveAndFlush(funcionarioNovo);
		
		funcionarioNovo2 = new Funcionario();
		funcionarioNovo2.setNome("Julia");
		funcionarioNovo2.setEmail("julia@email.com.br");
		funcionarioNovo2.setVoto(false);	
		
		this.funcionarioRepositorio.saveAndFlush(funcionarioNovo2);
		
		this.votacaoRepositorio.deleteAll();
		
		dataVoto = LocalDate.of(2019, 10, 30);
		
		votacao = new Votacao();
		votacao.setDataVoto(dataVoto);
		votacao.setFuncionario(funcionarioNovo);
		votacao.setRestaurante(restauranteNovo);
		
		this.votacaoRepositorio.saveAndFlush(votacao);
		
		votacao2 = new Votacao();
		votacao2.setDataVoto(dataVoto);
		votacao2.setFuncionario(funcionarioNovo2);
		votacao2.setRestaurante(restauranteNovo2);
		
		this.votacaoRepositorio.saveAndFlush(votacao2);
	}	
		
	@Test
	public void criarVotoSucesso() throws Exception {			
		assertThat(votacao.getId()).isNotNull();
		assertThat(votacao.getDataVoto()).isEqualTo(dataVoto);
		assertThat(votacao.getFuncionario()).isEqualTo(funcionarioNovo);
		assertThat(votacao.getRestaurante()).isEqualTo(restauranteNovo);
	}	
	
}
