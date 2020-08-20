package br.com.restaurante.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity 
@Table(name = "funcionario")
public class Funcionario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O campo nome é obrigatório.")
	@Column(name = "NOME", length = 80, nullable = false)
	private String nome;
	
	@NotBlank(message = "O campo e-mail é obrigatório.")
	@Column(name = "EMAIL", nullable = false, unique = true)
	@Email(message = "O e-mail precisa ser válido.")
	private String email;
	
	@Column(name = "VOTO")
	private boolean voto;

	public Funcionario(@NotBlank(message = "O campo nome é obrigatório.") String nome,
			@NotBlank(message = "O campo e-mail é obrigatório.") @Email(message = "O e-mail precisa ser válido.") String email,
			boolean voto) {
		super();
		this.nome = nome;
		this.email = email;
		this.voto = voto;
	}	

}
