package br.com.restaurante.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.restaurante.dto.RestauranteDto;
import br.com.restaurante.form.RestauranteForm;
import br.com.restaurante.modelo.Estado;
import br.com.restaurante.modelo.Restaurante;
import br.com.restaurante.repositorio.RestauranteRepositorio;

@Service
public class RestauranteServico {

	@Autowired
	private RestauranteRepositorio restauranteRepositorio;
	
	public List<RestauranteDto> listaRestaurantesDisponiveis(){
		return RestauranteDto.convertMoviesToDto(restauranteRepositorio.findByEstado(Estado.DISPONIVEL));
	}
	
	public Restaurante cadastrarNovoRestaurante(RestauranteForm restauranteForm) {
		String nomeRestaurante = restauranteForm.getNome().trim();
		Restaurante novoRestaurante = new Restaurante(nomeRestaurante,Estado.DISPONIVEL,0);
		restauranteRepositorio.saveAndFlush(novoRestaurante);
		return novoRestaurante;
	}
	
}
