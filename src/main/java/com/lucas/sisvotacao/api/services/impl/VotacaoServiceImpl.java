package com.lucas.sisvotacao.api.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.sisvotacao.api.entities.Votacao;
import com.lucas.sisvotacao.api.repositories.VotacaoRepository;
import com.lucas.sisvotacao.api.services.VotacaoService;

@Service
public class VotacaoServiceImpl implements VotacaoService {
	
	private static final Logger log = LoggerFactory.getLogger(VotacaoServiceImpl.class);
	
	@Autowired
	private VotacaoRepository votacaoRepository;

	@Override
	public Votacao receberVotoDeAssociadoEmPauta(Votacao votacao) {
		log.info("Recebendo Votação de Associado: {}", votacao);
		return this.votacaoRepository.save(votacao);
	}

	@Override
	public int contabilizarVotosPositivosPorPauta(int idPauta) {
		log.info("Contabilizando os votos Positivos da pauta: {}", idPauta);
		return this.votacaoRepository.findByVotosSim(idPauta);
	}

	@Override
	public int contabilizarVotosNegativosPorPauta(int idPauta) {
		log.info("Contabilizando os votos Negativos da pauta: {}", idPauta);
		return this.votacaoRepository.findByVotosNao(idPauta);
	}

	@Override
	public int verificarNumeroDeVezesAssociadoVotouEmPauta(int idPauta, int idAssociado) {
		log.info("Verificando número de vezes que associado de id {} votou na pauta de id {}", idAssociado, idPauta);
		return this.votacaoRepository.findByAssociadoAndPauta(idPauta, idAssociado);
	}

}
