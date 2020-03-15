package com.lucas.sisvotacao.api.dto;

public class ContagemVotosDto {
	
	private Integer votosSim;
	private Integer votosNao;
	private Integer idPauta;
	
	public Integer getVotosSim() {
		return votosSim;
	}
	public void setVotosSim(Integer votosSim) {
		this.votosSim = votosSim;
	}
	public Integer getVotosNao() {
		return votosNao;
	}
	public void setVotosNao(Integer votosNao) {
		this.votosNao = votosNao;
	}
	public Integer getIdPauta() {
		return idPauta;
	}
	public void setIdPauta(Integer idPauta) {
		this.idPauta = idPauta;
	}	

}
