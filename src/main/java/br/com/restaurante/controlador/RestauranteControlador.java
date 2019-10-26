package br.com.restaurante.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.restaurante.dto.RestauranteDto;
import br.com.restaurante.form.FuncionarioForm;
import br.com.restaurante.servico.RestauranteServico;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteControlador {

	private static final String VOTAR_RESTAURANTE = "/votar/{id}";

	@Autowired
	private RestauranteServico restauranteServico;
	

	@GetMapping
	public ResponseEntity<List<RestauranteDto>> listaRestaurantesDisponiveisParaVoto() {
		List<RestauranteDto> listaRestaurantes = this.restauranteServico.listaRestaurantesDisponiveis();
		if (listaRestaurantes.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(listaRestaurantes);
	}

	@PostMapping(VOTAR_RESTAURANTE)
	@Transactional
	public ResponseEntity<List<RestauranteDto>> votarRestaurante(@PathVariable(name = "id") Long id,
			@RequestBody @Valid FuncionarioForm funcionarioForm, BindingResult bindingResult, UriComponentsBuilder uriBuilder) {
		List<RestauranteDto> listaRestaurante = new ArrayList<>();
		if (!bindingResult.hasErrors()) {
			listaRestaurante = this.restauranteServico.votar(id,funcionarioForm);
			return ResponseEntity.ok().body(listaRestaurante);	

		}
		return ResponseEntity.notFound().build();		
	}

}
