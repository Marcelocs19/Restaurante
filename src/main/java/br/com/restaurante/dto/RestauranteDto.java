package br.com.restaurante.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.restaurante.modelo.Estado;
import br.com.restaurante.modelo.Restaurante;

public class RestauranteDto {

	private Long id;
	private String nome;
	private Estado estado;
	private int numeroVotos;
	
	public RestauranteDto(Restaurante restaurante) {
		this.id = restaurante.getId();
		this.nome = restaurante.getNome();
		this.estado = restaurante.getEstado();
		this.numeroVotos = restaurante.getNumeroVotos();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Estado getEstado() {
		return estado;
	}

	public int getNumeroVotos() {
		return numeroVotos;
	}
	
	public static List<RestauranteDto> converterRestauranteParaDto(List<Restaurante> listaRestaurantes) {
		return listaRestaurantes.stream().map(RestauranteDto::new).collect(Collectors.toList());
	}
	
}
