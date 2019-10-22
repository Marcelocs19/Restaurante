package br.com.restaurante.controlador;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.restaurante.dto.RestauranteDto;
import br.com.restaurante.form.RestauranteForm;
import br.com.restaurante.modelo.Restaurante;
import br.com.restaurante.servico.RestauranteServico;


@RestController
@RequestMapping("/restaurantes")
public class RestauranteControlador {

	private static final String ID_NOVO_RESTAURANTE = "/{id}";
	
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
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<RestauranteDto> novoRestaurante(@RequestBody @Valid RestauranteForm restauranteForm, BindingResult bindingResult,
			UriComponentsBuilder uriBuilder){
		HttpHeaders headers = new HttpHeaders();
		if(!bindingResult.hasErrors()) {
			Restaurante restaurante = restauranteServico.cadastrarNovoRestaurante(restauranteForm);
			RestauranteDto restauranteDto = new RestauranteDto(restaurante);
			URI uri = uriBuilder.path(ID_NOVO_RESTAURANTE).buildAndExpand(restaurante.getId()).toUri();
			headers.setLocation(uri);
			return new ResponseEntity<RestauranteDto>(restauranteDto,headers,HttpStatus.CREATED);
		}else {
			return new ResponseEntity<RestauranteDto>(headers,HttpStatus.BAD_REQUEST);
		}
	}
	
}
