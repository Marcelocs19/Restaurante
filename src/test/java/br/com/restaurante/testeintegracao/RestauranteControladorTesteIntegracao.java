package br.com.restaurante.testeintegracao;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.restaurante.RestauranteApplication;
import br.com.restaurante.modelo.Estado;
import br.com.restaurante.modelo.Funcionario;
import br.com.restaurante.modelo.Restaurante;
import br.com.restaurante.repositorio.FuncionarioRepositorio;
import br.com.restaurante.repositorio.RestauranteRepositorio;
import br.com.restaurante.repositorio.VotacaoRepositorio;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestauranteApplication.class)
@AutoConfigureMockMvc
public class RestauranteControladorTesteIntegracao {

	private static final String LISTA_RESTAURANTE = "/restaurantes";
		
	private static final Estado DISPONIVEL = Estado.DISPONIVEL;
	private static final Estado INDISPONIVEL = Estado.INDISPONIVEL;
	
	@Autowired
	private MockMvc mockMvc;
		
	@Autowired
	private RestauranteRepositorio restauranteRepositorio;
	
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;

	@Autowired
	private VotacaoRepositorio votacaoRepositorio;
	
	private Funcionario funcionario1;
	
	private Restaurante restaurante1;
	private Restaurante restaurante2;
	private Restaurante restaurante3;
	private Restaurante restaurante4;
	private Restaurante restaurante5;

	@Before
	public void antes() {
		this.restauranteRepositorio.deleteAll();
		this.funcionarioRepositorio.deleteAll();
		this.votacaoRepositorio.deleteAll();
		
		restaurante1 = new Restaurante();
		restaurante1.setNome("Pizzaria Fragata");
		restaurante1.setEstado(DISPONIVEL);
		restaurante1.setNumeroVotos(0);

		this.restauranteRepositorio.saveAndFlush(restaurante1);
		
		restaurante2 = new Restaurante();
		restaurante2.setNome("Churrascaria Freio de Ouro");
		restaurante2.setEstado(DISPONIVEL);
		restaurante2.setNumeroVotos(0);
		
		this.restauranteRepositorio.saveAndFlush(restaurante2);
		
		restaurante3 = new Restaurante();
		restaurante3.setNome("Restaurante Panorama");
		restaurante3.setEstado(DISPONIVEL);
		restaurante3.setNumeroVotos(0);

		this.restauranteRepositorio.saveAndFlush(restaurante3);
		
		restaurante4 = new Restaurante();
		restaurante4.setNome("Silva Lanches");
		restaurante4.setEstado(DISPONIVEL);
		restaurante4.setNumeroVotos(0);
		
		this.restauranteRepositorio.saveAndFlush(restaurante4);
		
		restaurante5 = new Restaurante();
		restaurante5.setNome("Hamburgueria");
		restaurante5.setEstado(INDISPONIVEL);
		LocalDate dataVitoria2 = LocalDate.of(2019, 10, 27);
		restaurante5.setDataVitoria(dataVitoria2);
		restaurante5.setNumeroVotos(0);

		this.restauranteRepositorio.saveAndFlush(restaurante5);
		
		funcionario1 = new Funcionario();
		funcionario1.setEmail("teste@email.com");
		funcionario1.setNome("Teste");
		funcionario1.setVoto(false);
		
		this.funcionarioRepositorio.saveAndFlush(funcionario1);
		
	}	
	
	@Test
	public void testeListarTodosRestaurantesDisponiveisSucesso() throws Exception {
		mockMvc.perform(get(LISTA_RESTAURANTE).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].id").value(restaurante1.getId()))
				.andExpect(jsonPath("$.[0].nome").value("Pizzaria Fragata"))
				.andExpect(jsonPath("$.[0].estado").value(DISPONIVEL.toString()))
				.andExpect(jsonPath("$.[1].id").value(restaurante2.getId()))
				.andExpect(jsonPath("$.[1].nome").value("Churrascaria Freio de Ouro"))
				.andExpect(jsonPath("$.[1].estado").value(DISPONIVEL.toString()))
				.andExpect(jsonPath("$.[2].id").value(restaurante3.getId()))
				.andExpect(jsonPath("$.[2].nome").value("Restaurante Panorama"))
				.andExpect(jsonPath("$.[2].estado").value(DISPONIVEL.toString()))
				.andExpect(jsonPath("$.[3].id").value(restaurante4.getId()))
				.andExpect(jsonPath("$.[3].nome").value("Silva Lanches"))
				.andExpect(jsonPath("$.[3].estado").value(DISPONIVEL.toString()));
	}
	
	@Test
	public void testeListarTodosRestaurantesDisponiveisErro() throws Exception {	
		this.restauranteRepositorio.deleteAll();
		mockMvc.perform(get(LISTA_RESTAURANTE).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	

	
}
