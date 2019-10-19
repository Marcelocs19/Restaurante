package br.com.restaurante.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity 
@Table(name = "restaurante")
public class Restaurante implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotBlank(message = "O campo nome é obrigatório.")
	@Column(name = "nome", length = 80, nullable = false)
	private String nome;
	
	@Enumerated(EnumType.STRING)
	private Estado estado;
	
	@Column(name = "numero_votos")
	private int numeroVotos;

	public Restaurante(@NotBlank(message = "O campo nome é obrigatório.") String nome, Estado estado, int numeroVotos) {
		super();
		this.nome = nome;
		this.estado = estado;
		this.numeroVotos = numeroVotos;
	}	
	
}
