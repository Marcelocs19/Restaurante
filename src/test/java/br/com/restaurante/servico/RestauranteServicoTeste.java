package br.com.restaurante.servico;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
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
import br.com.restaurante.modelo.Estado;
import br.com.restaurante.modelo.Funcionario;
import br.com.restaurante.modelo.Restaurante;
import br.com.restaurante.repositorio.FuncionarioRepositorio;
import br.com.restaurante.repositorio.RestauranteRepositorio;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestauranteApplication.class)
@AutoConfigureMockMvc
public class RestauranteServicoTeste {
	
	private static final Long TESTE1_ID_RESTAURANTE = 1L;
	private static final Long TESTE2_ID_RESTAURANTE = 2L;
	private static final Long TESTE3_ID_RESTAURANTE = 3L;
	private static final Long TESTE4_ID_RESTAURANTE = 4L;
	
	private static final Estado DISPONIVEL = Estado.DISPONIVEL;
	private static final Estado INDISPONIVEL = Estado.INDISPONIVEL;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private RestauranteServico restauranteServico;
	
	@MockBean
	private RestauranteRepositorio restauranteRepositorio;
	
	@MockBean
	private FuncionarioRepositorio funcionarioRepositorio;
	
	private Funcionario funcionario1;

	private Restaurante restaurante1;
	private Restaurante restaurante2;
	private Restaurante restaurante3;
	private Restaurante restaurante4;
	
	private List<Funcionario> listaFuncionario;
	private List<Restaurante> listaRestaurante;
	private List<RestauranteDto> listaRestauranteDto;
	
	@Before
	public void setup() {

		listaFuncionario = new ArrayList<>();
		listaRestaurante = new ArrayList<>();
		listaRestauranteDto = new ArrayList<>();

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

		restaurante4 = new Restaurante();
		restaurante4.setId(TESTE4_ID_RESTAURANTE);
		restaurante4.setNome("Silva Lanches");
		restaurante4.setEstado(INDISPONIVEL);

		funcionario1 = new Funcionario();
		funcionario1.setId(5L);
		funcionario1.setEmail("teste@email.com");
		funcionario1.setNome("Teste");
		funcionario1.setVoto(false);

		listaFuncionario.add(funcionario1);	

	}

	@Test
	public void testeListarTodosRestaurantesDisponiveisSucesso() throws Exception {
		listaRestaurante.addAll(Arrays.asList(restaurante1, restaurante2, restaurante3));
		when(restauranteRepositorio.findAllByOrderByNumeroVotosDesc()).thenReturn(listaRestaurante);
		List<RestauranteDto> listaRestaurantesDisponiveis = restauranteServico.listaRestaurantesDisponiveis();
		assertThat(listaRestaurantesDisponiveis.size()).isEqualTo(3);		
	}

}
