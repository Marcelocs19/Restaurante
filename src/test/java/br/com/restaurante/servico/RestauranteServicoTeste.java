package br.com.restaurante.servico;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.restaurante.RestauranteApplication;
import br.com.restaurante.dto.RestauranteDto;
import br.com.restaurante.form.FuncionarioForm;
import br.com.restaurante.modelo.Estado;
import br.com.restaurante.modelo.Funcionario;
import br.com.restaurante.modelo.Restaurante;
import br.com.restaurante.modelo.Votacao;
import br.com.restaurante.repositorio.FuncionarioRepositorio;
import br.com.restaurante.repositorio.RestauranteRepositorio;
import br.com.restaurante.repositorio.VotacaoRepositorio;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestauranteApplication.class)
@AutoConfigureMockMvc
public class RestauranteServicoTeste {

	private static final Estado DISPONIVEL = Estado.DISPONIVEL;
	private static final Estado INDISPONIVEL = Estado.INDISPONIVEL;

	@Autowired
	private RestauranteServico restauranteServico;

	@MockBean
	private RestauranteRepositorio restauranteRepositorioMock;

	@MockBean
	private FuncionarioRepositorio funcionarioRepositorio;

	@MockBean
	private VotacaoRepositorio votacaoRepositorio;

	private Funcionario funcionario1;
	private Funcionario funcionario2;

	private Restaurante restaurante1;
	private Restaurante restaurante2;
	private Restaurante restaurante3;
	private Restaurante restaurante4;

	private Votacao votacao1;

	private List<Funcionario> listaFuncionario;
	private List<Restaurante> listaRestauranteDisponiveis;
	private List<Restaurante> listaRestauranteIndisponiveis;
	private List<Restaurante> listaRestauranteVitoria;

	@Before
	public void setup() {

		listaFuncionario = new ArrayList<>();
		listaRestauranteDisponiveis = new ArrayList<>();
		listaRestauranteIndisponiveis = new ArrayList<>();
		listaRestauranteVitoria = new ArrayList<>();

		restaurante1 = new Restaurante();
		restaurante1.setId(1L);
		restaurante1.setNome("Pizzaria Fragata");
		restaurante1.setEstado(DISPONIVEL);
		restaurante1.setNumeroVotos(3);

		restaurante2 = new Restaurante();
		restaurante2.setId(2L);
		restaurante2.setNome("Churrascaria Freio de Ouro");
		restaurante2.setEstado(DISPONIVEL);
		restaurante2.setNumeroVotos(1);

		restaurante3 = new Restaurante();
		restaurante3.setId(3L);
		restaurante3.setNome("Restaurante Panorama");
		restaurante3.setEstado(DISPONIVEL);
		restaurante3.setNumeroVotos(2);

		LocalDate dataVitoria = LocalDate.of(2019, 10, 28);
		restaurante4 = new Restaurante();
		restaurante4.setId(4L);
		restaurante4.setNome("Silva Lanches");
		restaurante4.setDataVitoria(dataVitoria);
		restaurante4.setEstado(INDISPONIVEL);

		funcionario1 = new Funcionario();
		funcionario1.setId(5L);
		funcionario1.setEmail("teste@email.com");
		funcionario1.setNome("Teste");
		funcionario1.setVoto(false);

		funcionario2 = new Funcionario();
		funcionario2.setId(6L);
		funcionario2.setEmail("teste2@email.com");
		funcionario2.setNome("Teste2");
		funcionario2.setVoto(false);

		LocalDate dataVoto = LocalDate.of(2019, 10, 31);
		votacao1 = new Votacao();
		votacao1.setId(7L);
		votacao1.setDataVoto(dataVoto);
		votacao1.setFuncionario(funcionario1);
		votacao1.setRestaurante(restaurante2);

	}

	@Test
	public void testeListarTodosRestaurantesDisponiveis() throws Exception {
		listaRestauranteDisponiveis.addAll(Arrays.asList(restaurante1, restaurante3, restaurante2));
		when(restauranteRepositorioMock.findAllByOrderByNumeroVotosDesc()).thenReturn(listaRestauranteDisponiveis);
		List<RestauranteDto> listaRestaurantesDisponiveis = restauranteServico.listaRestaurantesDisponiveis();
		assertThat(listaRestaurantesDisponiveis.get(0).getNome()).isEqualTo("Pizzaria Fragata");
		assertThat(listaRestaurantesDisponiveis.get(1).getNome()).isEqualTo("Restaurante Panorama");
		assertThat(listaRestaurantesDisponiveis.get(2).getNome()).isEqualTo("Churrascaria Freio de Ouro");
	}

	@Test
	public void testeVotarRestauranteSucesso() throws Exception {
		Optional<Restaurante> restaurante = Optional.empty();
		restaurante = Optional.of(restaurante2);
		when(restauranteRepositorioMock.findById(restaurante2.getId())).thenReturn(restaurante);

		listaRestauranteDisponiveis.addAll(Arrays.asList(restaurante1, restaurante3, restaurante2));
		when(restauranteRepositorioMock.findAllByOrderByNumeroVotosDesc()).thenReturn(listaRestauranteDisponiveis);

		listaRestauranteIndisponiveis.add(restaurante4);
		when(restauranteRepositorioMock.findByEstado(INDISPONIVEL)).thenReturn(listaRestauranteIndisponiveis);

		listaRestauranteVitoria.add(restaurante4);
		when(restauranteRepositorioMock.findAllByOrderByDataVitoriaDesc()).thenReturn(listaRestauranteVitoria);

		when(votacaoRepositorio.saveAndFlush(votacao1)).thenReturn(votacao1);

		FuncionarioForm funcionarioForm = new FuncionarioForm();
		funcionarioForm.setEmail("teste@email.com");
		funcionarioForm.setNome("Teste");
		when(funcionarioRepositorio.findByEmail("teste@email.com")).thenReturn(funcionario1);

		listaFuncionario.addAll(Arrays.asList(funcionario1, funcionario2));
		when(funcionarioRepositorio.findByVoto(false)).thenReturn(listaFuncionario);

		List<RestauranteDto> listaRestaurantesVoto = restauranteServico.votar(restaurante2.getId(), funcionarioForm);

		assertThat(listaRestaurantesVoto.get(0).getNome()).isEqualTo("Pizzaria Fragata");
		assertThat(listaRestaurantesVoto.get(0).getNumeroVotos()).isEqualTo(3);
		assertThat(listaRestaurantesVoto.get(1).getNome()).isEqualTo("Restaurante Panorama");
		assertThat(listaRestaurantesVoto.get(1).getNumeroVotos()).isEqualTo(2);
		assertThat(listaRestaurantesVoto.get(2).getNome()).isEqualTo("Churrascaria Freio de Ouro");
		assertThat(listaRestaurantesVoto.get(2).getNumeroVotos()).isEqualTo(2);

	}

	@Test
	public void testeVotarRestauranteErroRestauranteNãoEncontrado() throws Exception {		
		listaRestauranteDisponiveis.addAll(Arrays.asList(restaurante1, restaurante3, restaurante2));
		when(restauranteRepositorioMock.findAllByOrderByNumeroVotosDesc()).thenReturn(listaRestauranteDisponiveis);
		
		FuncionarioForm funcionarioForm = new FuncionarioForm();
		funcionarioForm.setEmail("teste@email.com");
		funcionarioForm.setNome("Teste");
		when(funcionarioRepositorio.findByEmail("teste@email.com")).thenReturn(funcionario1);

		List<RestauranteDto> listaRestaurantesVoto = restauranteServico.votar(10L, funcionarioForm);

		assertThat(listaRestaurantesVoto.get(0).getNome()).isEqualTo("Pizzaria Fragata");
		assertThat(listaRestaurantesVoto.get(1).getNome()).isEqualTo("Restaurante Panorama");
		assertThat(listaRestaurantesVoto.get(2).getNome()).isEqualTo("Churrascaria Freio de Ouro");
	}

}
