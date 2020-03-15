package com.lucas.sisvotacao.api.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.sisvotacao.api.entities.Associado;
import com.lucas.sisvotacao.api.repositories.AssociadoRepository;
import com.lucas.sisvotacao.api.services.AssociadoService;

@Service
public class AssociadoServiceImpl implements AssociadoService {
	
	private static final Logger log = LoggerFactory.getLogger(AssociadoServiceImpl.class);
	
	@Autowired
	private AssociadoRepository associadoRespository;

	@Override
	public Associado localizarAssociadoPorId(int id) {
		log.info("Localizando associado pelo id={}", id);
		return this.associadoRespository.findById(id);
	}

	@Override
	public List<Associado> listarTodosAssociados() {
		log.info("Listando todos associados");
		return this.associadoRespository.findAll();
	}

	
}
