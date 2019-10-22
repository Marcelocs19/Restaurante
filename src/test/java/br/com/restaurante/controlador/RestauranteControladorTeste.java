package br.com.restaurante.controlador;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.restaurante.RestauranteApplication;
import br.com.restaurante.dto.RestauranteDto;
import br.com.restaurante.form.RestauranteForm;
import br.com.restaurante.modelo.Estado;
import br.com.restaurante.modelo.Restaurante;
import br.com.restaurante.servico.RestauranteServico;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestauranteApplication.class)
@AutoConfigureMockMvc
public class RestauranteControladorTeste {

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
	private RestauranteServico restauranteServico;

	private Restaurante restaurante1;
	private Restaurante restaurante2;
	private Restaurante restaurante3;
	private Restaurante restaurante4;

	private List<Restaurante> listaRestaurante;
	private List<RestauranteDto> listaRestauranteDto;

	@Before
	public void setup() {

		listaRestaurante = new ArrayList<>();
		listaRestauranteDto = new ArrayList<>();

		restaurante1 = new Restaurante();
		restaurante1.setId(TESTE1_ID_RESTAURANTE);
		restaurante1.setNome("Pizzaria Fragata");
		restaurante1.setEstado(DISPONIVEL);

		restaurante2 = new Restaurante();
		restaurante2.setId(TESTE2_ID_RESTAURANTE);
		restaurante2.setNome("Churrascaria Freio de Ouro");
		restaurante2.setEstado(DISPONIVEL);

		restaurante3 = new Restaurante();
		restaurante3.setId(TESTE3_ID_RESTAURANTE);
		restaurante3.setNome("Restaurante Panorama");
		restaurante3.setEstado(DISPONIVEL);

		restaurante4 = new Restaurante();
		restaurante4.setId(TESTE4_ID_RESTAURANTE);
		restaurante4.setNome("Silva Lanches");
		restaurante4.setEstado(INDISPONIVEL);

		listaRestaurante.addAll(Arrays.asList(restaurante1, restaurante2, restaurante3, restaurante4));

	}

	@Test
	public void testeListarTodosRestaurantesDisponiveisSucesso() throws Exception {
		listaRestauranteDto.addAll(RestauranteDto.convertMoviesToDto(listaRestaurante));
		given(this.restauranteServico.listaRestaurantesDisponiveis()).willReturn(listaRestauranteDto);
		mockMvc.perform(get(LISTA_RESTAURANTE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
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
	public void testeListarTodosRestaurantesDisponiveisErroListaVazia() throws Exception {
		listaRestauranteDto.clear();
		given(this.restauranteServico.listaRestaurantesDisponiveis()).willReturn(listaRestauranteDto);
		mockMvc.perform(get(LISTA_RESTAURANTE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void testeCadastrarNovoRestauranteSucesso() throws Exception {
		RestauranteForm restauranteForm = new RestauranteForm();
		restauranteForm.setNome("Restaurante Teste");
		ObjectMapper mapper = new ObjectMapper();
		String novoRestaurante = mapper.writeValueAsString(restauranteForm);
		mockMvc.perform(post(LISTA_RESTAURANTE)
				.content(novoRestaurante).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void testeCadastrarNovoRestauranteErroNomeRepetido() throws Exception {
		RestauranteForm restauranteForm = new RestauranteForm();
		restauranteForm.setNome("Restaurante Teste");
		ObjectMapper mapper = new ObjectMapper();
		String novoRestaurante = mapper.writeValueAsString(restauranteForm);
		mockMvc.perform(post(LISTA_RESTAURANTE)
				.content(novoRestaurante).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testeCadastrarNovoRestauranteErroSemNome() throws Exception {
		RestauranteForm restauranteForm = new RestauranteForm();
		restauranteForm.setNome(null);
		ObjectMapper mapper = new ObjectMapper();
		String novoRestaurante = mapper.writeValueAsString(restauranteForm);
		mockMvc.perform(post(LISTA_RESTAURANTE)
				.content(novoRestaurante).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}
	
	

}
