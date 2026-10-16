package model.movimentacao;

import java.sql.Date;

public class ModelLoteSerie {
	private String tipoItem_mov_ls;
	private String nomeItem_mov_ls;
	private String nomeProduto_mov_ls;
	private String tipo_mov_ls;
	private String lote_mov_ls;
	private String numSerie_mov_ls;
	private Date dataFab_mov_ls;
	private Date dataVal_mov_ls;
	private String fornecedor_mov_ls;
	
	public ModelLoteSerie() {}

	public ModelLoteSerie(String tipoItem_mov_ls, String nomeItem_mov_ls,
						  String nomeProduto_mov_ls, String tipo_mov_ls,
						  String lote_mov_ls, String numSerie_mov_ls,
						  Date dataFab_mov_ls, Date dataVal_mov_ls,
						  String fornecedor_mov_ls) {
		this();
		this.setTipoItem_mov_ls(tipoItem_mov_ls);
		this.setNomeItem_mov_ls(nomeItem_mov_ls);
		this.setNomeProduto_mov_ls(nomeProduto_mov_ls);
		this.setTipo_mov_ls(tipo_mov_ls);
		this.setLote_mov_ls(lote_mov_ls);
		this.setNumSerie_mov_ls(numSerie_mov_ls);
		this.setDataFab_mov_ls(dataFab_mov_ls);
		this.setDataVal_mov_ls(dataVal_mov_ls);
		this.setFornecedor_mov_ls(fornecedor_mov_ls);
	}

	public String getTipoItem_mov_ls() {
		return tipoItem_mov_ls;
	}

	public void setTipoItem_mov_ls(String tipoItem_mov_ls) {
		this.tipoItem_mov_ls = tipoItem_mov_ls;
	}

	public String getNomeItem_mov_ls() {
		return nomeItem_mov_ls;
	}

	public void setNomeItem_mov_ls(String nomeItem_mov_ls) {
		this.nomeItem_mov_ls = nomeItem_mov_ls;
	}

	public String getNomeProduto_mov_ls() {
		return nomeProduto_mov_ls;
	}

	public void setNomeProduto_mov_ls(String nomeProduto_mov_ls) {
		this.nomeProduto_mov_ls = nomeProduto_mov_ls;
	}

	public String getTipo_mov_ls() {
		return tipo_mov_ls;
	}

	public void setTipo_mov_ls(String tipo_mov_ls) {
		this.tipo_mov_ls = tipo_mov_ls;
	}

	public String getLote_mov_ls() {
		return lote_mov_ls;
	}

	public void setLote_mov_ls(String lote_mov_ls) {
		this.lote_mov_ls = lote_mov_ls;
	}

	public String getNumSerie_mov_ls() {
		return numSerie_mov_ls;
	}

	public void setNumSerie_mov_ls(String numSerie_mov_ls) {
		this.numSerie_mov_ls = numSerie_mov_ls;
	}

	public Date getDataFab_mov_ls() {
		return dataFab_mov_ls;
	}

	public void setDataFab_mov_ls(Date dataFab_mov_ls) {
		this.dataFab_mov_ls = dataFab_mov_ls;
	}

	public Date getDataVal_mov_ls() {
		return dataVal_mov_ls;
	}

	public void setDataVal_mov_ls(Date dataVal_mov_ls) {
		this.dataVal_mov_ls = dataVal_mov_ls;
	}

	public String getFornecedor_mov_ls() {
		return fornecedor_mov_ls;
	}

	public void setFornecedor_mov_ls(String fornecedor_mov_ls) {
		this.fornecedor_mov_ls = fornecedor_mov_ls;
	}
}