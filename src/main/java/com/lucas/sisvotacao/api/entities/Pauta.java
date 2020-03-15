package com.lucas.sisvotacao.api.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pauta")
public class Pauta implements Serializable{


	private static final long serialVersionUID = 7697150401429733981L;
	
	private Integer id;
	private String 	descricao;
	private Date 	inicio;
	private Date 	fim;
	private Integer totalSim;
	private Integer totalNao;
	private List<Votacao> votacoes;
	
	public Pauta() {
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "descricao", nullable = false)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "inicio", nullable = true)
	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	@Column(name = "fim", nullable = true)
	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	@Column(name = "total_sim", nullable = true)
	public Integer getTotalSim() {
		return totalSim;
	}

	public void setTotalSim(Integer totalSim) {
		this.totalSim = totalSim;
	}

	@Column(name = "total_nao", nullable = true)
	public Integer getTotalNao() {
		return totalNao;
	}

	public void setTotalNao(Integer totalNao) {
		this.totalNao = totalNao;
	}

	@OneToMany(mappedBy = "pauta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Votacao> getVotacoes() {
		return votacoes;
	}

	public void setVotacoes(List<Votacao> votacoes) {
		this.votacoes = votacoes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	
	
	
	

}
