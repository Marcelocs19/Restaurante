package br.com.restaurante.servico;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.restaurante.dto.RestauranteDto;
import br.com.restaurante.form.FuncionarioForm;
import br.com.restaurante.form.RestauranteForm;
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

	public Restaurante cadastrarNovoRestaurante(RestauranteForm restauranteForm) {
		String nomeRestaurante = restauranteForm.getNome().trim();
		Restaurante novoRestaurante = new Restaurante(nomeRestaurante, Estado.DISPONIVEL, 0);
		restauranteRepositorio.saveAndFlush(novoRestaurante);
		return novoRestaurante;
	}

	public Restaurante votar(Long id, @Valid FuncionarioForm funcionarioForm) {
		Optional<Restaurante> restaurante = restauranteRepositorio.findById(id);
		List<Funcionario> listaDosFuncionariosRestantesNaVotacao = funcionarioRepositorio.findByVoto(false);
		if (restaurante.isPresent() && !listaDosFuncionariosRestantesNaVotacao.isEmpty()) {
			Funcionario funcionario = funcionarioRepositorio.findByEmail(funcionarioForm.getEmail());
			if (funcionario != null && !funcionario.isVoto()) {
				Votacao votacao = new Votacao(funcionario, restaurante.get());
				votacaoRepositorio.saveAndFlush(votacao);
				funcionario.setVoto(true);
				restaurante.get().setNumeroVotos(restaurante.get().getNumeroVotos() + 1);
			}
			return restaurante.get();
		} else {
			List<Restaurante> listaRestauranteVencedor = restauranteRepositorio.findAllByOrderByNumeroVotosDesc();
			Restaurante vencedor = listaRestauranteVencedor.get(listaRestauranteVencedor.size() - 1);
			return vencedor;
		}
	}

}
