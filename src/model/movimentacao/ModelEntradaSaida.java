package model.movimentacao;

public class ModelEntradaSaida {
	private String tipoMov_mov_es;
	private String tipoItem_mov_es;
	private String nomeItem_mov_es;
	private String nomeProduto_mov_es;
	private int quant_mov_es;
	private String motivo_mov_es;
	private String doc_relacionado_mov_es;
	private String origem_mov_es;
	private String destino_mov_es;
	private String responsavel_mov_es;
	private String obs_mov_es;
	
	public ModelEntradaSaida() {}
	
	public ModelEntradaSaida(String tipoMov_mov_es, String tipoItem_mov_es,
							 String nomeItem_mov_es, String nomeProduto_mov_es,
							 int quant_mov_es, String motivo_mov_es,
							 String doc_relacionado_mov_es, String origem_mov_es,
							 String destino_mov_es, String responsavel_mov_es,
							 String obs_mov_es) {
		this();
		this.setTipoMov_mov_es(tipoMov_mov_es);
		this.setTipoItem_mov_es(tipoItem_mov_es);
		this.setNomeItem_mov_es(nomeItem_mov_es);
		this.setNomeProduto_mov_es(nomeProduto_mov_es);
		this.setQuant_mov_es(quant_mov_es);
		this.setMotivo_mov_es(motivo_mov_es);
		this.setDoc_relacionado_mov_es(doc_relacionado_mov_es);
		this.setOrigem_mov_es(origem_mov_es);
		this.setDestino_mov_es(destino_mov_es);
		this.setResponsavel_mov_es(responsavel_mov_es);
		this.setObs_mov_es(obs_mov_es);
	}

	public String getTipoMov_mov_es() {
		return tipoMov_mov_es;
	}

	public void setTipoMov_mov_es(String tipoMov_mov_es) {
		this.tipoMov_mov_es = tipoMov_mov_es;
	}

	public String getTipoItem_mov_es() {
		return tipoItem_mov_es;
	}

	public void setTipoItem_mov_es(String tipoItem_mov_es) {
		this.tipoItem_mov_es = tipoItem_mov_es;
	}

	public String getNomeItem_mov_es() {
		return nomeItem_mov_es;
	}

	public void setNomeItem_mov_es(String nomeItem_mov_es) {
		this.nomeItem_mov_es = nomeItem_mov_es;
	}

	public String getNomeProduto_mov_es() {
		return nomeProduto_mov_es;
	}

	public void setNomeProduto_mov_es(String nomeProduto_mov_es) {
		this.nomeProduto_mov_es = nomeProduto_mov_es;
	}

	public int getQuant_mov_es() {
		return quant_mov_es;
	}

	public void setQuant_mov_es(int quant_mov_es) {
		this.quant_mov_es = quant_mov_es;
	}

	public String getMotivo_mov_es() {
		return motivo_mov_es;
	}

	public void setMotivo_mov_es(String motivo_mov_es) {
		this.motivo_mov_es = motivo_mov_es;
	}

	public String getDoc_relacionado_mov_es() {
		return doc_relacionado_mov_es;
	}

	public void setDoc_relacionado_mov_es(String doc_relacionado_mov_es) {
		this.doc_relacionado_mov_es = doc_relacionado_mov_es;
	}

	public String getOrigem_mov_es() {
		return origem_mov_es;
	}

	public void setOrigem_mov_es(String origem_mov_es) {
		this.origem_mov_es = origem_mov_es;
	}

	public String getDestino_mov_es() {
		return destino_mov_es;
	}

	public void setDestino_mov_es(String destino_mov_es) {
		this.destino_mov_es = destino_mov_es;
	}

	public String getResponsavel_mov_es() {
		return responsavel_mov_es;
	}

	public void setResponsavel_mov_es(String responsavel_mov_es) {
		this.responsavel_mov_es = responsavel_mov_es;
	}

	public String getObs_mov_es() {
		return obs_mov_es;
	}

	public void setObs_mov_es(String obs_mov_es) {
		this.obs_mov_es = obs_mov_es;
	}
}