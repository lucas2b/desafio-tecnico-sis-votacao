package com.lucas.sisvotacao.api.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class CadastroDePautaDto {
	
	private Integer id;
	private String  descricao;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@NotEmpty
	@Length(min = 5, max = 200, message = "Descrição da Pauta deve ter entre 5 e 200 caracteres.")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	@Override
	public String toString() {
		return "Pauta Dto = [id=" + id + ", descricao=" + descricao + "]";
	}

}
