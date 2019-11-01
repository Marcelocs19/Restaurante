package br.com.restaurante.testeunitario.controlador;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.restaurante.RestauranteApplication;
import br.com.restaurante.controlador.RestauranteControlador;
import br.com.restaurante.dto.RestauranteDto;
import br.com.restaurante.modelo.Estado;
import br.com.restaurante.modelo.Restaurante;
import br.com.restaurante.servico.RestauranteServico;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestauranteApplication.class)
@AutoConfigureMockMvc
public class RestauranteControladorTeste {

	private static final Long TESTE1_ID_RESTAURANTE = 1L;
	private static final Long TESTE2_ID_RESTAURANTE = 2L;
	private static final Long TESTE3_ID_RESTAURANTE = 3L;
	private static final Long TESTE4_ID_RESTAURANTE = 4L;

	private static final Estado DISPONIVEL = Estado.DISPONIVEL;
	private static final Estado INDISPONIVEL = Estado.INDISPONIVEL;
	
	@MockBean
	private RestauranteServico restauranteServico;
	
	@Autowired
	private RestauranteControlador restauranteControlador;

	private Restaurante restaurante1;
	private Restaurante restaurante2;
	private Restaurante restaurante3;
	private Restaurante restaurante4;

	private List<Restaurante> listaRestaurante;
	private List<RestauranteDto> listaRestauranteDtoMock;

	@Before
	public void setup() {

		listaRestaurante = new ArrayList<>();
		listaRestauranteDtoMock = new ArrayList<>();

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
	public void testeListarTodosRestaurantesDisponiveisParaVotoSucesso() throws Exception {
		listaRestaurante.addAll(Arrays.asList(restaurante1, restaurante2, restaurante3, restaurante4));
		listaRestauranteDtoMock.addAll(RestauranteDto.converterRestauranteParaDto(listaRestaurante));
		when(restauranteServico.listaRestaurantesDisponiveis()).thenReturn(listaRestauranteDtoMock);
		
		 ResponseEntity<List<RestauranteDto>> listaRestaurantes = restauranteControlador.listaRestaurantesDisponiveisParaVoto();
		 
		 assertThat(listaRestaurantes.getBody()).isEqualTo(listaRestauranteDtoMock);		

	}	
	
	@Test
	public void testeListarTodosRestaurantesDisponiveisParaVotoErro() throws Exception {
		when(restauranteServico.listaRestaurantesDisponiveis()).thenReturn(listaRestauranteDtoMock);
		
		 ResponseEntity<List<RestauranteDto>> listaRestaurantes = restauranteControlador.listaRestaurantesDisponiveisParaVoto();
		 
		 assertThat(listaRestaurantes.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);		

	}	
	

}