package com.lucas.sisvotacao.api.services;

import java.util.List;

import com.lucas.sisvotacao.api.entities.Associado;

public interface AssociadoService {
	
	Associado localizarAssociadoPorId(int id);
	
	List<Associado> listarTodosAssociados();

}
