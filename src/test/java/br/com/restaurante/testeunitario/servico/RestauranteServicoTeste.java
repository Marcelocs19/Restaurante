package br.com.restaurante.testeunitario.servico;

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

import br.com.restaurante.dto.RestauranteDto;
import br.com.restaurante.form.FuncionarioForm;
import br.com.restaurante.modelo.Estado;
import br.com.restaurante.modelo.Funcionario;
import br.com.restaurante.modelo.Restaurante;
import br.com.restaurante.modelo.Votacao;
import br.com.restaurante.repositorio.FuncionarioRepositorio;
import br.com.restaurante.repositorio.RestauranteRepositorio;
import br.com.restaurante.repositorio.VotacaoRepositorio;
import br.com.restaurante.servico.RestauranteServico;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestauranteServicoTeste {

	private static final Long TESTE1_ID_RESTAURANTE = 1L;
	private static final Long TESTE2_ID_RESTAURANTE = 2L;
	private static final Long TESTE3_ID_RESTAURANTE = 3L;
	private static final Long TESTE4_ID_RESTAURANTE = 4L;
	private static final Long TESTE5_ID_RESTAURANTE = 5L;
	private static final Long TESTE6_ID_RESTAURANTE = 6L;
	private static final Long TESTE7_ID_RESTAURANTE = 7L;
	private static final Long TESTE8_ID_RESTAURANTE = 8L;
	
	private static final Long TESTE1_ID_FUNCIONARIO = 9L;
	private static final Long TESTE2_ID_FUNCIONARIO = 10L;
	private static final Long TESTE3_ID_FUNCIONARIO = 11L;
	private static final Long TESTE4_ID_FUNCIONARIO = 12L;
	
	private static final Long TESTE1_ID_VOTACAO = 13L;
	
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
	private Funcionario funcionario3;
	private Funcionario funcionario4;
	
	private Restaurante restaurante1;
	private Restaurante restaurante2;
	private Restaurante restaurante3;
	private Restaurante restaurante4;
	private Restaurante restaurante5;
	private Restaurante restaurante6;
	private Restaurante restaurante7;
	private Restaurante restaurante8;

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
		restaurante1.setId(TESTE1_ID_RESTAURANTE);
		restaurante1.setNome("Pizzaria Fragata");
		restaurante1.setEstado(DISPONIVEL);
		restaurante1.setNumeroVotos(3);

		restaurante2 = new Restaurante();
		restaurante2.setId(TESTE2_ID_RESTAURANTE);
		restaurante2.setNome("Churrascaria Freio de Ouro");
		restaurante2.setEstado(DISPONIVEL);
		restaurante2.setNumeroVotos(1);

		restaurante3 = new Restaurante();
		restaurante3.setId(TESTE3_ID_RESTAURANTE);
		restaurante3.setNome("Restaurante Panorama");
		restaurante3.setEstado(DISPONIVEL);
		restaurante3.setNumeroVotos(2);

		LocalDate dataVitoria = LocalDate.of(2019, 10, 28);
		restaurante4 = new Restaurante();
		restaurante4.setId(TESTE4_ID_RESTAURANTE);
		restaurante4.setNome("Silva Lanches");
		restaurante4.setDataVitoria(dataVitoria);
		restaurante4.setEstado(INDISPONIVEL);
		
		LocalDate dataVitoria2 = LocalDate.of(2019, 10, 29);
		restaurante5 = new Restaurante();
		restaurante5.setId(TESTE5_ID_RESTAURANTE);
		restaurante5.setNome("Servero Hamburgueria");
		restaurante5.setDataVitoria(dataVitoria2);
		restaurante5.setEstado(INDISPONIVEL);
		
		LocalDate dataVitoria3 = LocalDate.of(2019, 10, 30);
		restaurante6 = new Restaurante();
		restaurante6.setId(TESTE6_ID_RESTAURANTE);
		restaurante6.setNome("Restaurante Vitória");
		restaurante6.setDataVitoria(dataVitoria3);
		restaurante6.setEstado(INDISPONIVEL);
		
		LocalDate dataVitoria4 = LocalDate.of(2019, 11, 1);
		restaurante7 = new Restaurante();
		restaurante7.setId(TESTE7_ID_RESTAURANTE);
		restaurante7.setNome("Espaço32");
		restaurante7.setDataVitoria(dataVitoria4);
		restaurante7.setEstado(INDISPONIVEL);
		
		LocalDate dataVitoria5 = LocalDate.of(2019, 11, 4);
		restaurante8 = new Restaurante();
		restaurante8.setId(TESTE8_ID_RESTAURANTE);
		restaurante8.setNome("Mazá");
		restaurante8.setDataVitoria(dataVitoria5);
		restaurante8.setEstado(INDISPONIVEL);

		funcionario1 = new Funcionario();
		funcionario1.setId(TESTE1_ID_FUNCIONARIO);
		funcionario1.setEmail("teste@email.com");
		funcionario1.setNome("Teste");
		funcionario1.setVoto(false);

		funcionario2 = new Funcionario();
		funcionario2.setId(TESTE2_ID_FUNCIONARIO);
		funcionario2.setEmail("teste2@email.com");
		funcionario2.setNome("Teste2");
		funcionario2.setVoto(false);
		
		funcionario3 = new Funcionario();
		funcionario3.setId(TESTE3_ID_FUNCIONARIO);
		funcionario3.setEmail("teste3@email.com");
		funcionario3.setNome("Teste3");
		funcionario3.setVoto(true);
		
		funcionario4 = new Funcionario();
		funcionario4.setId(TESTE4_ID_FUNCIONARIO);
		funcionario4.setEmail("teste4@email.com");
		funcionario4.setNome("Teste4");
		funcionario4.setVoto(false);
		

		LocalDate dataVoto = LocalDate.of(2019, 10, 31);
		votacao1 = new Votacao();
		votacao1.setId(TESTE1_ID_VOTACAO);
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

		List<RestauranteDto> listaRestaurantesVoto = restauranteServico.votar(TESTE2_ID_RESTAURANTE, funcionarioForm);

		assertThat(listaRestaurantesVoto.get(0).getNome()).isEqualTo("Pizzaria Fragata");
		assertThat(listaRestaurantesVoto.get(1).getNome()).isEqualTo("Restaurante Panorama");
		assertThat(listaRestaurantesVoto.get(2).getNome()).isEqualTo("Churrascaria Freio de Ouro");

	}

	@Test
	public void testeVotarRestauranteErroRestauranteNãoEncontrado() throws Exception {		
		listaRestauranteDisponiveis.addAll(Arrays.asList(restaurante1, restaurante3, restaurante2));
		when(restauranteRepositorioMock.findAllByOrderByNumeroVotosDesc()).thenReturn(listaRestauranteDisponiveis);
		
		FuncionarioForm funcionarioForm = new FuncionarioForm();
		funcionarioForm.setEmail("teste@email.com");
		funcionarioForm.setNome("Teste");
		when(funcionarioRepositorio.findByEmail("teste@email.com")).thenReturn(funcionario1);

		List<RestauranteDto> listaRestaurantesVoto = restauranteServico.votar(20L, funcionarioForm);
		assertThat(listaRestaurantesVoto.size()).isEqualTo(0);
//		assertThat(listaRestaurantesVoto.get(0).getNome()).isEqualTo("Pizzaria Fragata");
//		assertThat(listaRestaurantesVoto.get(1).getNome()).isEqualTo("Restaurante Panorama");
//		assertThat(listaRestaurantesVoto.get(2).getNome()).isEqualTo("Churrascaria Freio de Ouro");
	}
	
	@Test
	public void testeVotarRestauranteErroFuncionarioNãoEncontrado() throws Exception {		
		listaRestauranteDisponiveis.addAll(Arrays.asList(restaurante1, restaurante3, restaurante2));
		when(restauranteRepositorioMock.findAllByOrderByNumeroVotosDesc()).thenReturn(listaRestauranteDisponiveis);
		
		FuncionarioForm funcionarioForm = new FuncionarioForm();
		funcionarioForm.setEmail("abc@email.com");
		funcionarioForm.setNome("Abc");

		List<RestauranteDto> listaRestaurantesVoto = restauranteServico.votar(TESTE2_ID_RESTAURANTE, funcionarioForm);

		assertThat(listaRestaurantesVoto.size()).isEqualTo(0);
		
//		assertThat(listaRestaurantesVoto.get(0).getNome()).isEqualTo("Pizzaria Fragata");
//		assertThat(listaRestaurantesVoto.get(0).getNumeroVotos()).isEqualTo(3);
//		assertThat(listaRestaurantesVoto.get(1).getNome()).isEqualTo("Restaurante Panorama");
//		assertThat(listaRestaurantesVoto.get(1).getNumeroVotos()).isEqualTo(2);
//		assertThat(listaRestaurantesVoto.get(2).getNome()).isEqualTo("Churrascaria Freio de Ouro");
//		assertThat(listaRestaurantesVoto.get(2).getNumeroVotos()).isEqualTo(1);
	}

	@Test
	public void testeVotarRestauranteErroFuncionarioJaVotou() throws Exception {	
		listaRestauranteDisponiveis.addAll(Arrays.asList(restaurante1, restaurante3, restaurante2));
		when(restauranteRepositorioMock.findAllByOrderByNumeroVotosDesc()).thenReturn(listaRestauranteDisponiveis);
		
		Optional<Restaurante> restaurante = Optional.empty();
		restaurante = Optional.of(restaurante2);
		when(restauranteRepositorioMock.findById(TESTE2_ID_RESTAURANTE)).thenReturn(restaurante);
		
		listaRestauranteIndisponiveis.add(restaurante4);
		when(restauranteRepositorioMock.findByEstado(INDISPONIVEL)).thenReturn(listaRestauranteIndisponiveis);
		
		listaRestauranteVitoria.add(restaurante4);
		when(restauranteRepositorioMock.findAllByOrderByDataVitoriaDesc()).thenReturn(listaRestauranteVitoria);
		
		listaFuncionario.addAll(Arrays.asList(funcionario1, funcionario2));
		when(funcionarioRepositorio.findByVoto(false)).thenReturn(listaFuncionario);
		
		FuncionarioForm funcionarioForm = new FuncionarioForm();
		funcionarioForm.setEmail("teste3@email.com");
		funcionarioForm.setNome("Teste3");
		when(funcionarioRepositorio.findByEmail("teste3@email.com")).thenReturn(funcionario3);

		List<RestauranteDto> listaRestaurantesVoto = restauranteServico.votar(TESTE2_ID_RESTAURANTE, funcionarioForm);
		
		assertThat(listaRestaurantesVoto.get(0).getNome()).isEqualTo("Pizzaria Fragata");
		assertThat(listaRestaurantesVoto.get(1).getNome()).isEqualTo("Restaurante Panorama");
		assertThat(listaRestaurantesVoto.get(2).getNome()).isEqualTo("Churrascaria Freio de Ouro");
	}
	
	@Test
	public void testeVotarRestauranteLimparListaRestaurante() throws Exception {
		Optional<Restaurante> restaurante = Optional.empty();
		restaurante = Optional.of(restaurante2);
		when(restauranteRepositorioMock.findById(TESTE2_ID_RESTAURANTE)).thenReturn(restaurante);
		
		restaurante1.setNumeroVotos(0);
		restaurante2.setNumeroVotos(0);
		restaurante3.setNumeroVotos(0);
		listaRestauranteDisponiveis.addAll(Arrays.asList(restaurante2, restaurante1, restaurante3
				,restaurante4,restaurante5,restaurante6,restaurante7,restaurante8));
		when(restauranteRepositorioMock.findAllByOrderByNumeroVotosDesc()).thenReturn(listaRestauranteDisponiveis);
		
		listaRestauranteIndisponiveis.addAll(Arrays.asList(restaurante4,restaurante5,restaurante6,restaurante7,restaurante8));
		when(restauranteRepositorioMock.findByEstado(INDISPONIVEL)).thenReturn(listaRestauranteIndisponiveis);
		
		listaRestauranteVitoria.addAll(Arrays.asList(restaurante8,restaurante7,restaurante6,restaurante5,restaurante4));
		when(restauranteRepositorioMock.findAllByOrderByDataVitoriaDesc()).thenReturn(listaRestauranteVitoria);
		
		listaFuncionario.addAll(Arrays.asList(funcionario1, funcionario2));
		when(funcionarioRepositorio.findByVoto(false)).thenReturn(listaFuncionario);
		
		FuncionarioForm funcionarioForm = new FuncionarioForm();
		funcionarioForm.setEmail("teste4@email.com");
		funcionarioForm.setNome("Teste4");
		when(funcionarioRepositorio.findByEmail("teste4@email.com")).thenReturn(funcionario4);
		
		List<RestauranteDto> listaRestaurantesVoto = restauranteServico.votar(TESTE2_ID_RESTAURANTE, funcionarioForm);
		
		assertThat(listaRestaurantesVoto.get(0).getNome()).isEqualTo("Churrascaria Freio de Ouro");
		assertThat(listaRestaurantesVoto.get(1).getNome()).isEqualTo("Pizzaria Fragata");		
		assertThat(listaRestaurantesVoto.get(2).getNome()).isEqualTo("Restaurante Panorama");		
		assertThat(listaRestaurantesVoto.get(3).getNome()).isEqualTo("Silva Lanches");
		assertThat(listaRestaurantesVoto.get(4).getNome()).isEqualTo("Servero Hamburgueria");
		assertThat(listaRestaurantesVoto.get(5).getNome()).isEqualTo("Restaurante Vitória");
		assertThat(listaRestaurantesVoto.get(6).getNome()).isEqualTo("Espaço32");
		assertThat(listaRestaurantesVoto.get(7).getNome()).isEqualTo("Mazá");
		
	}
	
	@Test
	public void testeVotarRestauranteGanhaNoDia() throws Exception {	
		Optional<Restaurante> restaurante = Optional.empty();
		restaurante = Optional.of(restaurante2);
		when(restauranteRepositorioMock.findById(restaurante2.getId())).thenReturn(restaurante);
		restaurante1.setNumeroVotos(2);
		restaurante2.setNumeroVotos(0);
		restaurante3.setNumeroVotos(0);
		List<Restaurante> listaRestauranteOrdenadosPelosVotos = new ArrayList<>();
		listaRestauranteOrdenadosPelosVotos.addAll(Arrays.asList(restaurante1, restaurante2, restaurante3));
		when(restauranteRepositorioMock.findAllByOrderByNumeroVotosDesc()).thenReturn(listaRestauranteOrdenadosPelosVotos);
		
		listaRestauranteDisponiveis.addAll(Arrays.asList(restaurante2,restaurante3));
		when(restauranteRepositorioMock.findByEstado(DISPONIVEL)).thenReturn(listaRestauranteDisponiveis);
		
		List<Restaurante> listaRestauranteVitoria = new ArrayList<>();
		listaRestauranteVitoria.addAll(Arrays.asList(restaurante1,restaurante2,restaurante3));
		when(restauranteRepositorioMock.findAllByOrderByDataVitoriaDesc()).thenReturn(listaRestauranteVitoria);
				
		List<Funcionario> listaFuncionarioVoto = new ArrayList<>();
		when(funcionarioRepositorio.findByVoto(false)).thenReturn(listaFuncionarioVoto);		
		
		funcionario1.setVoto(true);
		funcionario2.setVoto(true);
		List<Funcionario> listaTodosFuncionarios = new ArrayList<>();
		listaTodosFuncionarios.addAll(Arrays.asList(funcionario1,funcionario2));
		
		FuncionarioForm funcionarioForm = new FuncionarioForm();
		funcionarioForm.setEmail(funcionario1.getEmail());
		funcionarioForm.setNome(funcionario1.getEmail());
		when(funcionarioRepositorio.findByEmail(funcionario1.getEmail())).thenReturn(funcionario1);
		
		List<RestauranteDto> listaRestaurantesVoto = restauranteServico.votar(TESTE2_ID_RESTAURANTE, funcionarioForm);
		assertThat(listaRestaurantesVoto.get(0).getNome()).isEqualTo("Pizzaria Fragata");
		assertThat(listaRestaurantesVoto.get(0).getEstado()).isEqualTo(INDISPONIVEL);
	}
	
}
