package br.com.restaurante.repositorio;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.After;
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
import br.com.restaurante.modelo.Restaurante;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestauranteApplication.class)
public class RestauranteRepositorioTeste {
	
	@Autowired
	private RestauranteRepositorio restauranteRepositorio;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	private Restaurante restauranteNovo;
	private Restaurante restauranteNovo2;
	private Restaurante restauranteNovo3;
	private Restaurante restauranteNovo4;
	private Restaurante restauranteNovo5;
	
	@Before
	public void antes() {
		this.restauranteRepositorio.deleteAll();
		
		restauranteNovo = new Restaurante();
		restauranteNovo.setNome("Churrascaria");
		restauranteNovo.setEstado(Estado.DISPONIVEL);
		restauranteNovo.setNumeroVotos(2);
		
		this.restauranteRepositorio.saveAndFlush(restauranteNovo);
		
		restauranteNovo2 = new Restaurante();
		restauranteNovo2.setNome("Galeteria");
		restauranteNovo2.setEstado(Estado.DISPONIVEL);
		restauranteNovo2.setNumeroVotos(1);
		
		this.restauranteRepositorio.saveAndFlush(restauranteNovo2);
		
		restauranteNovo3 = new Restaurante();
		restauranteNovo3.setNome("Pizzaria");
		restauranteNovo3.setEstado(Estado.DISPONIVEL);
		restauranteNovo3.setNumeroVotos(3);
		
		this.restauranteRepositorio.saveAndFlush(restauranteNovo3);
		
		restauranteNovo4 = new Restaurante();
		restauranteNovo4.setNome("Casa de Massas");
		restauranteNovo4.setEstado(Estado.INDISPONIVEL);
		LocalDate dataVitoria = LocalDate.of(2019, 10, 28);
		restauranteNovo4.setDataVitoria(dataVitoria);
		restauranteNovo4.setNumeroVotos(0);
		
		this.restauranteRepositorio.saveAndFlush(restauranteNovo4);
		
		restauranteNovo5 = new Restaurante();
		restauranteNovo5.setNome("Hamburgueria");
		restauranteNovo5.setEstado(Estado.INDISPONIVEL);
		LocalDate dataVitoria2 = LocalDate.of(2019, 10, 27);
		restauranteNovo5.setDataVitoria(dataVitoria2);
		restauranteNovo5.setNumeroVotos(0);
		
		this.restauranteRepositorio.saveAndFlush(restauranteNovo5);
	}
	
	@After
	public void depois() {
		this.restauranteRepositorio.deleteAll();
	}
	
	@Test
	public void criarRestauranteSucesso() throws Exception {			
		assertThat(restauranteNovo.getId()).isNotNull();
		assertThat(restauranteNovo.getNome()).isEqualTo("Churrascaria");
		assertThat(restauranteNovo.getEstado()).isEqualTo(Estado.DISPONIVEL);
		assertThat(restauranteNovo.getNumeroVotos()).isEqualTo(2);
		
		assertThat(restauranteNovo2.getId()).isNotNull();
		assertThat(restauranteNovo2.getNome()).isEqualTo("Galeteria");
		assertThat(restauranteNovo2.getEstado()).isEqualTo(Estado.DISPONIVEL);
		assertThat(restauranteNovo2.getNumeroVotos()).isEqualTo(1);
		
		assertThat(restauranteNovo3.getId()).isNotNull();
		assertThat(restauranteNovo3.getNome()).isEqualTo("Pizzaria");
		assertThat(restauranteNovo3.getEstado()).isEqualTo(Estado.DISPONIVEL);
		assertThat(restauranteNovo3.getNumeroVotos()).isEqualTo(3);
		
		assertThat(restauranteNovo4.getId()).isNotNull();
		assertThat(restauranteNovo4.getNome()).isEqualTo("Casa de Massas");
		assertThat(restauranteNovo4.getEstado()).isEqualTo(Estado.INDISPONIVEL);
		LocalDate dataVitoria = LocalDate.of(2019, 10, 28);
		assertThat(restauranteNovo4.getDataVitoria()).isEqualTo(dataVitoria);
		assertThat(restauranteNovo4.getNumeroVotos()).isEqualTo(0);
		
		assertThat(restauranteNovo5.getId()).isNotNull();
		assertThat(restauranteNovo5.getNome()).isEqualTo("Hamburgueria");
		assertThat(restauranteNovo5.getEstado()).isEqualTo(Estado.INDISPONIVEL);
		LocalDate dataVitoria2 = LocalDate.of(2019, 10, 27);
		assertThat(restauranteNovo5.getDataVitoria()).isEqualTo(dataVitoria2);
		assertThat(restauranteNovo5.getNumeroVotos()).isEqualTo(0);
		
	}
	
	@Test
	public void criarRestauranteNomeErro() throws Exception {	
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("O campo nome é obrigatório.");
		this.restauranteRepositorio.saveAndFlush(new Restaurante("",Estado.DISPONIVEL,0));		
	}
	
	@Test
	public void listaRestaurantesOrdenadaPelosMaioresVotosSucesso() throws Exception {
		List<Restaurante> listaRestaurantes = this.restauranteRepositorio.findAllByOrderByNumeroVotosDesc();
		assertThat(listaRestaurantes.get(0).getNumeroVotos()).isEqualTo(3);
		assertThat(listaRestaurantes.get(1).getNumeroVotos()).isEqualTo(2);
		assertThat(listaRestaurantes.get(2).getNumeroVotos()).isEqualTo(1);
		assertThat(listaRestaurantes.get(3).getNumeroVotos()).isEqualTo(0);
		assertThat(listaRestaurantes.get(4).getNumeroVotos()).isEqualTo(0);
	}
	
	@Test
	public void listaRestaurantesOrdenadaPelosMaioresVotosVazia() throws Exception {
		this.restauranteRepositorio.deleteAll();
		List<Restaurante> listaRestaurantes = this.restauranteRepositorio.findAllByOrderByNumeroVotosDesc();
		assertThat(listaRestaurantes.size()).isZero();
	}
	
	@Test
	public void listaRestaurantesOrdenadaPelaDataVitoriaSucesso() throws Exception {
		List<Restaurante> listaRestaurantes = this.restauranteRepositorio.findAllByOrderByDataVitoriaDesc();
		LocalDate dataVitoria = LocalDate.of(2019, 10, 28);
		LocalDate dataVitoria2 = LocalDate.of(2019, 10, 27);
		assertThat(listaRestaurantes.get(0).getDataVitoria()).isEqualTo(dataVitoria);
		assertThat(listaRestaurantes.get(1).getDataVitoria()).isEqualTo(dataVitoria2);
		assertThat(listaRestaurantes.get(2).getDataVitoria()).isNull();
		assertThat(listaRestaurantes.get(3).getDataVitoria()).isNull();
		assertThat(listaRestaurantes.get(4).getDataVitoria()).isNull();
	}

	@Test
	public void listaRestaurantesPeloEstadoDisponivel() throws Exception {
		List<Restaurante> listaRestaurantes = this.restauranteRepositorio.findByEstado(Estado.DISPONIVEL);
		assertThat(listaRestaurantes.size()).isEqualTo(3);
	}
	
	@Test
	public void listaRestaurantesPeloEstadoIndisponiveis() throws Exception {
		List<Restaurante> listaRestaurantes = this.restauranteRepositorio.findByEstado(Estado.INDISPONIVEL);
		assertThat(listaRestaurantes.size()).isEqualTo(2);
	}
	
	@Test
	public void removerRestaurante() throws Exception {
		this.restauranteRepositorio.delete(restauranteNovo3);
		assertThat(restauranteRepositorio.findAll().size()).isEqualTo(4);
	}
	
}
