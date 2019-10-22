package br.com.restaurante.form;

import javax.validation.constraints.NotBlank;

public class RestauranteForm {
	
	@NotBlank(message = "O campo nome é obrigatório.")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	

}
