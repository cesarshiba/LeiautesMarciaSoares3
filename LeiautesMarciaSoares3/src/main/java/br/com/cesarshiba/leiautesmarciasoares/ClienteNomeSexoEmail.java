package br.com.cesarshiba.leiautesmarciasoares;

public class ClienteNomeSexoEmail {
	private String nomeCliente;
	private String sexoCliente;
	private String emailCliente;
	private String objetivoCliente;
	
	
	public ClienteNomeSexoEmail(String nomeCliente, String sexoCliente, String emailCliente, String objetivoCliente) {
		this.nomeCliente = nomeCliente;
		this.sexoCliente = sexoCliente;
		this.emailCliente = emailCliente;
		this.objetivoCliente = objetivoCliente;
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


	public String getObjetivoCliente() {
		return objetivoCliente;
	}


	public void setObjetivoCliente(String objetivoCliente) {
		this.objetivoCliente = objetivoCliente;
	}

}
