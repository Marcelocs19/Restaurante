package br.com.restaurante.servico;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.restaurante.dto.RestauranteDto;
import br.com.restaurante.form.FuncionarioForm;
import br.com.restaurante.modelo.Estado;
import br.com.restaurante.modelo.Funcionario;
import br.com.restaurante.modelo.Restaurante;
import br.com.restaurante.modelo.Votacao;
import br.com.restaurante.repositorio.FuncionarioRepositorio;
import br.com.restaurante.repositorio.RestauranteRepositorio;
import br.com.restaurante.repositorio.VotacaoRepositorio;

@Service
public class RestauranteServico {

	@Autowired
	private RestauranteRepositorio restauranteRepositorio;

	@Autowired
	private VotacaoRepositorio votacaoRepositorio;

	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;

	public List<RestauranteDto> listaRestaurantesDisponiveis() {
		return RestauranteDto.converterRestauranteParaDto(restauranteRepositorio.findAllByOrderByNumeroVotosDesc());
	}

	public List<RestauranteDto> votar(Long id, @Valid FuncionarioForm funcionarioForm) {
		Optional<Restaurante> restaurante = restauranteRepositorio.findById(id);
		List<Restaurante> restauranteVencendor = new ArrayList<>();
		if (restaurante.isPresent() && restaurante.get().getEstado().equals(Estado.DISPONIVEL)) {
			Funcionario funcionario = funcionarioRepositorio.findByEmail(funcionarioForm.getEmail());
			if (validarVoto(funcionario)) {
				criarVoto(funcionario, restaurante.get());
				restaurante.get().setNumeroVotos(restaurante.get().getNumeroVotos() + 1);
				funcionario.setVoto(true);
				return listaRestaurantesDisponiveis();
			} else {
				List<Funcionario> funcionariosFaltamVotar = funcionarioRepositorio.findByVoto(false);
				if(funcionariosFaltamVotar.isEmpty()) {
					restauranteVencendor.add(restauranteVencedor());
					return RestauranteDto.converterRestauranteParaDto(restauranteVencendor);
				}else {
					return listaRestaurantesDisponiveis();
				}				
			}
		} else {
			List<RestauranteDto> lista = new ArrayList<>();
			return lista;
		}
	}

	private boolean validarVoto(Funcionario funcionario) {
		boolean valido = false;	
		LocalDate data = LocalDate.now();
		DayOfWeek diaSemana = data.getDayOfWeek();
		
		List<Restaurante> restaurantesVencedores = restauranteRepositorio.findByEstado(Estado.INDISPONIVEL);
		
		if (!diaSemana.name().equals("SATURDAY") && !diaSemana.name().equals("SUNDAY")) {
			if (primeiroVotacaoSemRestauranteVencedor(funcionario)) {
				valido = primeiroVotacaoSemRestauranteVencedor(funcionario);
			} else {
				Restaurante vencedor = restauranteRepositorio.findAllByOrderByDataVitoriaDesc().get(0);
				LocalDate dataVitoriaRestaurante = pegaDataRestaurante(vencedor);
				if (!data.equals(dataVitoriaRestaurante)) {
					valido = validaVotoFuncionario(restaurantesVencedores,funcionario);
				}
			}
			return valido;
		} else {
			return valido;
		}
	}
	
	private boolean primeiroVotacaoSemRestauranteVencedor(Funcionario funcionario) {
		boolean validar = false;
		if (!funcionario.isVoto()) {
			if (restauranteRepositorio.findAllByOrderByDataVitoriaDesc().get(0).getDataVitoria() == null) {
				validar = true;
			}
		}
		return validar;
	}
	
	private LocalDate pegaDataRestaurante(Restaurante restaurante) {
		return restaurante.getDataVitoria();
	}
	
	private boolean validaVotoFuncionario(List<Restaurante> restaurantesVencedores, Funcionario funcionario) {
		boolean valida = false;
		if (!funcionario.isVoto() && restaurantesVencedores.size() <= 4) {
			valida = true;
		} else {
			if (funcionario.isVoto()) {
				valida = false;
			} else {
				recomecarVotacaoRestaurante(restaurantesVencedores);
				valida = true;
			}
		}
		return valida;
	}
	
	private void recomecarVotacaoRestaurante(List<Restaurante> restaurantesVencedores) {
		LocalDate data = LocalDate.now();
		DayOfWeek diaSemana = data.getDayOfWeek();
		if ((!diaSemana.toString().equals("SATURDAY")) || (!diaSemana.toString().equals("SUNDAY"))) {
			for (int i = 0; i < restaurantesVencedores.size(); i++) {
				restaurantesVencedores.get(i).setEstado(Estado.DISPONIVEL);
			}
		}
	}
	
	private void criarVoto(Funcionario funcionario, Restaurante restaurante) {
		Votacao voto = new Votacao();
		voto.setFuncionario(funcionario);
		voto.setRestaurante(restaurante);
		votacaoRepositorio.saveAndFlush(voto);
	}	

	private Restaurante restauranteVencedor() {
		LocalDate data = LocalDate.now();
		Restaurante vencedor = restauranteRepositorio.findAllByOrderByNumeroVotosDesc().get(0);
		vencedor.setDataVitoria(data);
		vencedor.setEstado(Estado.INDISPONIVEL);
		vencedor.setNumeroVotos(0);
		limparVotosFuncionario();
		limparVotosRestaurantes();
		return vencedor;
	}

	private void limparVotosFuncionario() {
		List<Funcionario> listaLimparVotos = funcionarioRepositorio.findAll();
		for (int i = 0; i < listaLimparVotos.size(); i++) {
			listaLimparVotos.get(i).setVoto(false);
		}
	}

	private void limparVotosRestaurantes() {
		List<Restaurante> listaLimparVotos = restauranteRepositorio.findByEstado(Estado.DISPONIVEL);
		for (int i = 0; i < listaLimparVotos.size(); i++) {
			listaLimparVotos.get(i).setNumeroVotos(0);
		}
	}

}
