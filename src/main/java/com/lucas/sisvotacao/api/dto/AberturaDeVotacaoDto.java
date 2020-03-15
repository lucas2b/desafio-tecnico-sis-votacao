package com.lucas.sisvotacao.api.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;


public class AberturaDeVotacaoDto {
	
	private Integer id;
	private Integer tempoDeSessaoMinutos;
	private String	dataInicioSessao;
	private String 	dataFimSessao;

	public String getDataInicioSessao() {
		return dataInicioSessao;
	}

	public void setDataInicioSessao(String dataInicioSessao) {
		this.dataInicioSessao = dataInicioSessao;
	}

	public String getDataFimSessao() {
		return dataFimSessao;
	}

	public void setDataFimSessao(String dataFimSessao) {
		this.dataFimSessao = dataFimSessao;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTempoDeSessaoMinutos() {
		return tempoDeSessaoMinutos;
	}

	public void setTempoDeSessaoMinutos(Integer tempoDeSessaoMinutos) {
		this.tempoDeSessaoMinutos = tempoDeSessaoMinutos;
	}



	@Override
	public String toString() {
		return "Pauta Dto = [id=" + id + ", tempoDeSessaoMinutos=" + tempoDeSessaoMinutos + "]";
	}

}
