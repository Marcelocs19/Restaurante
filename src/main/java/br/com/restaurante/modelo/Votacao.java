package br.com.restaurante.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "votacao")
public class Votacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "DATA_VOTO", nullable = true) 
	private LocalDate dataVoto = LocalDate.now();
	
	@OneToOne
	private Funcionario funcionario;
	
	@ManyToOne
	private Restaurante restaurante;

	public Votacao(Funcionario funcionario, Restaurante restaurante) {
		super();
		this.funcionario = funcionario;
		this.restaurante = restaurante;
	}
	
}
