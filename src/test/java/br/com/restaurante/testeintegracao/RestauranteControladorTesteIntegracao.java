package br.com.restaurante.testeintegracao;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.restaurante.RestauranteApplication;
import br.com.restaurante.modelo.Estado;
import br.com.restaurante.modelo.Restaurante;
import br.com.restaurante.repositorio.FuncionarioRepositorio;
import br.com.restaurante.repositorio.RestauranteRepositorio;
import br.com.restaurante.repositorio.VotacaoRepositorio;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestauranteApplication.class)
@AutoConfigureMockMvc
public class RestauranteControladorTesteIntegracao {

	private static final String LISTA_RESTAURANTE = "/restaurantes";
	
	private static final Long TESTE1_ID_RESTAURANTE = 1L;
	private static final Long TESTE2_ID_RESTAURANTE = 2L;
	private static final Long TESTE3_ID_RESTAURANTE = 3L;
	private static final Long TESTE4_ID_RESTAURANTE = 4L;

	private static final Estado DISPONIVEL = Estado.DISPONIVEL;
	private static final Estado INDISPONIVEL = Estado.INDISPONIVEL;
	
	@Autowired
	private MockMvc mockMvc;
		
	@MockBean
	private RestauranteRepositorio restauranteRepositorioMock;
	
	@MockBean
	private FuncionarioRepositorio funcionarioRepositorio;

	@MockBean
	private VotacaoRepositorio votacaoRepositorio;
	
	private Restaurante restaurante1;
	private Restaurante restaurante2;
	private Restaurante restaurante3;
	private Restaurante restaurante4;

	private List<Restaurante> listaRestaurante;

	@Before
	public void setup() {

		listaRestaurante = new ArrayList<>();

		restaurante1 = new Restaurante();
		restaurante1.setId(TESTE1_ID_RESTAURANTE);
		restaurante1.setNome("Pizzaria Fragata");
		restaurante1.setEstado(DISPONIVEL);
		restaurante1.setNumeroVotos(0);

		restaurante2 = new Restaurante();
		restaurante2.setId(TESTE2_ID_RESTAURANTE);
		restaurante2.setNome("Churrascaria Freio de Ouro");
		restaurante2.setEstado(DISPONIVEL);
		restaurante2.setNumeroVotos(0);
		
		restaurante3 = new Restaurante();
		restaurante3.setId(TESTE3_ID_RESTAURANTE);
		restaurante3.setNome("Restaurante Panorama");
		restaurante3.setEstado(DISPONIVEL);
		restaurante3.setNumeroVotos(0);

		restaurante4 = new Restaurante();
		restaurante4.setId(TESTE4_ID_RESTAURANTE);
		restaurante4.setNome("Silva Lanches");
		restaurante4.setEstado(INDISPONIVEL);
		restaurante4.setNumeroVotos(0);

	}
	
	@Test
	public void testeListarTodosRestaurantesDisponiveisSucesso() throws Exception {
		listaRestaurante.addAll(Arrays.asList(restaurante1, restaurante2, restaurante3));
		when(restauranteRepositorioMock.findAllByOrderByNumeroVotosDesc()).thenReturn(listaRestaurante);				
		
		mockMvc.perform(get(LISTA_RESTAURANTE).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].id").value(TESTE1_ID_RESTAURANTE))
				.andExpect(jsonPath("$.[0].nome").value("Pizzaria Fragata"))
				.andExpect(jsonPath("$.[0].estado").value(DISPONIVEL.toString()))
				.andExpect(jsonPath("$.[1].id").value(TESTE2_ID_RESTAURANTE))
				.andExpect(jsonPath("$.[1].nome").value("Churrascaria Freio de Ouro"))
				.andExpect(jsonPath("$.[1].estado").value(DISPONIVEL.toString()))
				.andExpect(jsonPath("$.[2].id").value(TESTE3_ID_RESTAURANTE))
				.andExpect(jsonPath("$.[2].nome").value("Restaurante Panorama"))
				.andExpect(jsonPath("$.[2].estado").value(DISPONIVEL.toString()));
	}
	
	@Test
	public void testeListarTodosRestaurantesDisponiveisErro() throws Exception {
		when(restauranteRepositorioMock.findAllByOrderByNumeroVotosDesc()).thenReturn(listaRestaurante);				
		
		mockMvc.perform(get(LISTA_RESTAURANTE).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	
	
}
