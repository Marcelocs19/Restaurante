package br.com.restaurante.erro;

public class ErroValidacaoDto {
	
	private String campo;
	private String mensagemErro;
	
	public ErroValidacaoDto(String campo, String mensagemErro) {
		super();
		this.campo = campo;
		this.mensagemErro = mensagemErro;
	}

	public String getCampo() {
		return campo;
	}

	public String getMensagemErro() {
		return mensagemErro;
	}
		

}
