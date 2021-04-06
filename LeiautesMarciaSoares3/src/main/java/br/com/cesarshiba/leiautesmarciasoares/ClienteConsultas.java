package br.com.cesarshiba.leiautesmarciasoares;

public class ClienteConsultas {
	private String nomeCliente;
	private String dataConsulta;
	private String estadoNutricional;
	private String planoAlimentar;
	private String prescricaoDietetica;
	private String recomendacoes;

	
	public String getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(String dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public ClienteConsultas(String nomeCliente, String dataConsulta, String estadoNutricional, String planoAlimentar, String prescricaoDietetica, String recomendacoes) {
		this.nomeCliente = nomeCliente;
		this.dataConsulta = dataConsulta;
		this.estadoNutricional = estadoNutricional;
		this.planoAlimentar = planoAlimentar;
		this.prescricaoDietetica = prescricaoDietetica;
		this.recomendacoes = recomendacoes;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getEstadoNutricional() {
		return estadoNutricional;
	}

	public void setEstadoNutricional(String estadoNutricional) {
		this.estadoNutricional = estadoNutricional;
	}

	public String getPlanoAlimentar() {
		return planoAlimentar;
	}

	public void setPlanoAlimentar(String planoAlimentar) {
		this.planoAlimentar = planoAlimentar;
	}

	public String getPrescricaoDietetica() {
		return prescricaoDietetica;
	}

	public void setPrescricaoDietetica(String prescricaoDietetica) {
		this.prescricaoDietetica = prescricaoDietetica;
	}

	public String getRecomendacoes() {
		return recomendacoes;
	}

	public void setRecomendacoes(String recomendacoes) {
		this.recomendacoes = recomendacoes;
	}
}