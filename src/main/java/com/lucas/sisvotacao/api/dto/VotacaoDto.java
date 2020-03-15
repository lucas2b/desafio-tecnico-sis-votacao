package com.lucas.sisvotacao.api.dto;

public class VotacaoDto {
	
	private Integer pautaId;
	private Integer associadoId;
	private boolean voto;
	
	public Integer getPautaId() {
		return pautaId;
	}
	public void setPautaId(Integer pautaId) {
		this.pautaId = pautaId;
	}
	public Integer getAssociadoId() {
		return associadoId;
	}
	public void setAssociadoId(Integer associadoId) {
		this.associadoId = associadoId;
	}
	public boolean isVoto() {
		return voto;
	}
	public void setVoto(boolean voto) {
		this.voto = voto;
	}

}
