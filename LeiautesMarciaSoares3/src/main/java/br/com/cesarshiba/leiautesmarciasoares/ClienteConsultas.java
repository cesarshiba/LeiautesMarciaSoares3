package br.com.cesarshiba.leiautesmarciasoares;

public class ClienteConsultas {
	private String nomeCliente;
	private String dataConsulta;

	
	public String getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(String dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public ClienteConsultas(String nomeCliente, String dataConsulta) {
		this.nomeCliente = nomeCliente;
		this.dataConsulta = dataConsulta;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
}