package br.com.restaurante.repositorio;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.restaurante.RestauranteApplication;
import br.com.restaurante.modelo.Funcionario;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestauranteApplication.class)
public class FuncionarioRepositorioTeste {
	
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	private Funcionario funcionarioNovo;
	private Funcionario funcionarioNovo2;
	
	@Before
	public void antes() {
		this.funcionarioRepositorio.deleteAll();
		
		funcionarioNovo = new Funcionario();
		funcionarioNovo.setNome("Wiliam");
		funcionarioNovo.setEmail("wiliam@email.com.br");
		funcionarioNovo.setVoto(false);		
		this.funcionarioRepositorio.saveAndFlush(funcionarioNovo);
		
		funcionarioNovo2 = new Funcionario();
		funcionarioNovo2.setNome("Julia");
		funcionarioNovo2.setEmail("julia@email.com.br");
		funcionarioNovo2.setVoto(true);	
		this.funcionarioRepositorio.saveAndFlush(funcionarioNovo2);
		
	}
	
	@After
	public void depois() {
		this.funcionarioRepositorio.deleteAll();
	}
	
	@Test
	public void criarFuncionarioSucesso() throws Exception {			
		assertThat(funcionarioNovo.getId()).isNotNull();
		assertThat(funcionarioNovo.getNome()).isEqualTo("Wiliam");
		assertThat(funcionarioNovo.getEmail()).isEqualTo("wiliam@email.com.br");		
	}
	
	@Test
	public void criarFuncionarioNomeErro() throws Exception {	
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("O campo nome é obrigatório.");
		this.funcionarioRepositorio.saveAndFlush(new Funcionario("","teste@email.com",false));		
	}
	
	@Test
	public void criarFuncionarioEmailErro() throws Exception {	
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("O campo e-mail é obrigatório.");
		this.funcionarioRepositorio.saveAndFlush(new Funcionario("Teste","",false));		
	}	
	
	@Test
	public void buscarFuncionarioPorEmailSucesso() throws Exception {		
		Funcionario busca = this.funcionarioRepositorio.findByEmail(funcionarioNovo.getEmail());		
		assertThat(busca.getEmail()).isEqualTo("wiliam@email.com.br");			
	}
	
	@Test
	public void buscarFuncionarioPorEmailErro() throws Exception {		
		assertThat(this.funcionarioRepositorio.findByEmail("erro@email.com.br")).isNull();			
	}		
	
	@Test
	public void listaFuncionariosQueJaVotarao() throws Exception {
		List<Funcionario> listaFuncionariosJaVotarao = this.funcionarioRepositorio.findByVoto(true);
		assertThat(listaFuncionariosJaVotarao.size()).isEqualTo(1);
	}
	
	@Test
	public void listaFuncionariosQueNãoVotarao() throws Exception {
		List<Funcionario> listaFuncionariosJaVotarao = this.funcionarioRepositorio.findByVoto(false);
		assertThat(listaFuncionariosJaVotarao.size()).isEqualTo(1);
	}
	
	@Test
	public void removerFuncionario() throws Exception {
		this.funcionarioRepositorio.delete(funcionarioNovo);			
		assertThat(this.funcionarioRepositorio.findByEmail("wiliam@email.com.br")).isNull();
	}
	

}
