package br.com.cesarshiba.leiautesmarciasoares;

public class ClienteNomeSexoEmail {
	private String nomeCliente;
	private String sexoCliente;
	private String emailCliente;
	
	
	public ClienteNomeSexoEmail(String nomeCliente, String sexoCliente, String emailCliente) {
		this.nomeCliente = nomeCliente;
		this.sexoCliente = sexoCliente;
		this.emailCliente = emailCliente;
	}


	public String getNomeCliente() {
		return nomeCliente;
	}


	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}


	public String getSexoCliente() {
		return sexoCliente;
	}


	public void setSexoCliente(String sexoCliente) {
		this.sexoCliente = sexoCliente;
	}


	public String getEmailCliente() {
		return emailCliente;
	}


	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

}
