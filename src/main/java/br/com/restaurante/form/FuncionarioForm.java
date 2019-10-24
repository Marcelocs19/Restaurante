package br.com.restaurante.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class FuncionarioForm {
	
	@NotBlank(message = "O campo nome é obrigatório.")
	private String nome;

	@NotBlank(message = "O campo e-mail é obrigatório.")
	@Email(message = "O e-mail precisa ser válido.")
	private String email;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
