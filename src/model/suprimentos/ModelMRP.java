package model.suprimentos;

import java.time.LocalDate;

public class ModelMRP {
	private int IdSugestao;
	private String tipoItem;
	private String statusSugestao;
	
	private int quantSugerida;
	private int quantSolicitada;
	private LocalDate dataPrevistaReposicao;
	private int IdResponsavel;
	private String justificativa;
	
	public ModelMRP() {}
	
	public ModelMRP(int IdSugestao, String tipoItem,
					String statusSugestao, int quantSugerida,
					int quantSolicitada, LocalDate dataPrevistaReposicao,
					int IdResponsavel, String justificativa) {
	this();
	this.setIdSugestao(IdSugestao);
	this.setTipoItem(tipoItem);
	this.setStatusSugestao(statusSugestao);
	this.setQuantSugerida(quantSugerida);
	this.setQuantSolicitada(quantSolicitada);
	this.setDataPrevistaReposicao(dataPrevistaReposicao);
	this.setIdResponsavel(IdResponsavel);
	this.setJustificativa(justificativa);
	}

	public int getIdSugestao() {
		return IdSugestao;
	}

	public void setIdSugestao(int idSugestao) {
		IdSugestao = idSugestao;
	}

	public String getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(String tipoItem) {
		this.tipoItem = tipoItem;
	}

	public String getStatusSugestao() {
		return statusSugestao;
	}

	public void setStatusSugestao(String statusSugestao) {
		this.statusSugestao = statusSugestao;
	}

	public int getQuantSugerida() {
		return quantSugerida;
	}

	public void setQuantSugerida(int quantSugerida) {
		this.quantSugerida = quantSugerida;
	}

	public int getQuantSolicitada() {
		return quantSolicitada;
	}

	public void setQuantSolicitada(int quantSolicitada) {
		this.quantSolicitada = quantSolicitada;
	}

	public LocalDate getDataPrevistaReposicao() {
		return dataPrevistaReposicao;
	}

	public void setDataPrevistaReposicao(LocalDate dataPrevistaReposicao) {
		this.dataPrevistaReposicao = dataPrevistaReposicao;
	}

	public int getIdResponsavel() {
		return IdResponsavel;
	}

	public void setIdResponsavel(int idResponsavel) {
		IdResponsavel = idResponsavel;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
}