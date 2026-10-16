package model.movimentacao;

public class ModelMultilocalizacao {
	private String tipoItem_mov_mult;
	private String nomeItem_mov_mult;
	private String nomeProduto_mov_mult;
	
	private String origem_mov_mult;
	private String lote_mov_mult;
	private String doc_mov_mult;
	private String destino_mov_mult;
	private double quant_mov_mult;
	private int numSerie_mov_mult;
	private String obs_mov_mult;
	
	public ModelMultilocalizacao() {}
	
	public ModelMultilocalizacao(String tipoItem_mov_mult, String nomeItem_mov_mult,
								 String nomeProduto_mov_mult, String origem_mov_mult,
								 String lote_mov_mult, String doc_mov_mult,
								 String destino_mov_mult, double quant_mov_mult,
								 int numSerie_mov_mult, String obs_mov_mult) {
		this();
		this.setTipoItem_mov_mult(tipoItem_mov_mult);
		this.setNomeItem_mov_mult(nomeItem_mov_mult);
		this.setNomeProduto_mov_mult(nomeProduto_mov_mult);
		
		this.setOrigem_mov_mult(origem_mov_mult);
		this.setLote_mov_mult(lote_mov_mult);
		this.setDoc_mov_mult(doc_mov_mult);
		this.setDestino_mov_mult(destino_mov_mult);
		this.setQuant_mov_mult(quant_mov_mult);
		this.setNumSerie_mov_mult(numSerie_mov_mult);
		this.setObs_mov_mult(obs_mov_mult);
	}

	public String getTipoItem_mov_mult() {
		return tipoItem_mov_mult;
	}

	public void setTipoItem_mov_mult(String tipoItem_mov_mult) {
		this.tipoItem_mov_mult = tipoItem_mov_mult;
	}

	public String getNomeItem_mov_mult() {
		return nomeItem_mov_mult;
	}

	public void setNomeItem_mov_mult(String nomeItem_mov_mult) {
		this.nomeItem_mov_mult = nomeItem_mov_mult;
	}

	public String getNomeProduto_mov_mult() {
		return nomeProduto_mov_mult;
	}

	public void setNomeProduto_mov_mult(String nomeProduto_mov_mult) {
		this.nomeProduto_mov_mult = nomeProduto_mov_mult;
	}

	public String getOrigem_mov_mult() {
		return origem_mov_mult;
	}

	public void setOrigem_mov_mult(String origem_mov_mult) {
		this.origem_mov_mult = origem_mov_mult;
	}

	public String getLote_mov_mult() {
		return lote_mov_mult;
	}

	public void setLote_mov_mult(String lote_mov_mult) {
		this.lote_mov_mult = lote_mov_mult;
	}

	public String getDoc_mov_mult() {
		return doc_mov_mult;
	}

	public void setDoc_mov_mult(String doc_mov_mult) {
		this.doc_mov_mult = doc_mov_mult;
	}

	public String getDestino_mov_mult() {
		return destino_mov_mult;
	}

	public void setDestino_mov_mult(String destino_mov_mult) {
		this.destino_mov_mult = destino_mov_mult;
	}

	public double getQuant_mov_mult() {
		return quant_mov_mult;
	}

	public void setQuant_mov_mult(double quant_mov_mult) {
		this.quant_mov_mult = quant_mov_mult;
	}

	public int getNumSerie_mov_mult() {
		return numSerie_mov_mult;
	}

	public void setNumSerie_mov_mult(int numSerie_mov_mult) {
		this.numSerie_mov_mult = numSerie_mov_mult;
	}

	public String getObs_mov_mult() {
		return obs_mov_mult;
	}

	public void setObs_mov_mult(String obs_mov_mult) {
		this.obs_mov_mult = obs_mov_mult;
	}
}