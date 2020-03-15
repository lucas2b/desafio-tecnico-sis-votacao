package com.lucas.sisvotacao.api.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.sisvotacao.api.entities.Pauta;
import com.lucas.sisvotacao.api.repositories.PautaRepository;
import com.lucas.sisvotacao.api.services.PautaService;

@Service
public class PautaServiceImpl implements PautaService {
	
	private static final Logger log = LoggerFactory.getLogger(PautaServiceImpl.class);
	
	@Autowired
	private PautaRepository pautaRepository;

	@Override
	public Pauta cadastrarNovaPauta(Pauta pauta) {
		log.info("Cadastrando nova pauta: {}", pauta);
		return this.pautaRepository.save(pauta);
	}

	@Override
	public Pauta abrirSessaoDeVotacaoEmPauta(Pauta pauta) {
		log.info("Abrindo Nova Sessão de Votação em Pauta: {}", pauta);
		return this.pautaRepository.save(pauta);
	}

	@Override
	public Pauta localizarPautaPorId(int idPauta) {
		log.info("Localizando pauta pelo Id: {}", idPauta);
		return this.pautaRepository.findById(idPauta);
	}

	@Override
	public List<Pauta> listarTodasPautas() {
		log.info("Listando todas pautas cadastradas");
		return this.pautaRepository.findAll();
	}

}
