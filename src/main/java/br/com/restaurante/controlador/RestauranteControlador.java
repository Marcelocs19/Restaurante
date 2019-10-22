package br.com.restaurante.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.restaurante.dto.RestauranteDto;
import br.com.restaurante.servico.RestauranteServico;


@RestController
@RequestMapping("/restaurantes")
public class RestauranteControlador {

	@Autowired
	private RestauranteServico restauranteServico;
	
	@GetMapping
	public ResponseEntity<List<RestauranteDto>> listaRestaurantesDisponiveisParaVoto() {
		List<RestauranteDto> listaRestaurantes = restauranteServico.listaRestaurantesDisponiveis();
		if (listaRestaurantes.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(listaRestaurantes);
	}
	
}
