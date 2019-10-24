package br.com.restaurante.servico;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		return RestauranteDto.convertMoviesToDto(restauranteRepositorio.findByEstado(Estado.DISPONIVEL));
	}

	public Restaurante votar(Long id, @Valid FuncionarioForm funcionarioForm) {
		Optional<Restaurante> restaurante = restauranteRepositorio.findById(id);

		if (restaurante.isPresent()) {
			Funcionario funcionario = funcionarioRepositorio.findByEmail(funcionarioForm.getEmail());
			if (validarVoto(funcionario)) {
				criarVoto(funcionario, restaurante.get());
				restaurante.get().setNumeroVotos(restaurante.get().getNumeroVotos() + 1);
				funcionario.setVoto(true);
			} else {
				return restauranteVencedor();
			}
		}
		return restaurante.get();
	}

	private Restaurante restauranteVencedor() {
		LocalDateTime data = LocalDateTime.now();
		Restaurante vencedor = restauranteRepositorio.findAllByOrderByNumeroVotosDesc().get(0);
		vencedor.setDataVitoria(data);
		vencedor.setEstado(Estado.INDISPONIVEL);
		vencedor.setNumeroVotos(0);
		limparVotosFuncionario();
		limparVotosRestaurantes();
		return vencedor;
	}

	private void criarVoto(Funcionario funcionario, Restaurante restaurante) {
		Votacao voto = new Votacao();
		voto.setFuncionario(funcionario);
		voto.setRestaurante(restaurante);
		votacaoRepositorio.saveAndFlush(voto);
	}

	private boolean validarVoto(Funcionario funcionario) {
		boolean valido = true;
		LocalDateTime data = LocalDateTime.now();
		List<Restaurante> restauranteVencedor = restauranteRepositorio.findByDataVitoria(data);
		if (restauranteVencedor.isEmpty()) {
			if (funcionario.isVoto()) {
				valido = false;
			}
		} else {
			Restaurante vencedor = restauranteRepositorio.findAllByOrderByDataVitoriaDesc().get(0);
			String dataAtual = pegaDataAtual();
			String dataVitoriaRestaurante = pegaDataRestaurante(vencedor);
			if(dataAtual.equals(dataVitoriaRestaurante)) {
				if (funcionario.isVoto()) {
					valido = false;
				}
			}
		}
		return valido;
	}
	
	private String pegaDataRestaurante(Restaurante restaurante) {
		LocalDateTime data = restaurante.getDataVitoria();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		String dataVotacao = data.format(formatter).substring(0, 10);
		return dataVotacao;
	}

	private String pegaDataAtual() {
		LocalDateTime data = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		String dataAtual = data.format(formatter).substring(0, 10);
		return dataAtual;
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
