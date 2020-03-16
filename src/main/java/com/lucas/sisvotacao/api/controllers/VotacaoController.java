package com.lucas.sisvotacao.api.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.lucas.sisvotacao.api.dto.ContagemVotosDto;
import com.lucas.sisvotacao.api.dto.ValidaCpfAssociadoDto;
import com.lucas.sisvotacao.api.dto.VotacaoDto;
import com.lucas.sisvotacao.api.entities.Associado;
import com.lucas.sisvotacao.api.entities.Pauta;
import com.lucas.sisvotacao.api.entities.Votacao;
import com.lucas.sisvotacao.api.response.ResponseWrapper;
import com.lucas.sisvotacao.api.services.AssociadoService;
import com.lucas.sisvotacao.api.services.PautaService;
import com.lucas.sisvotacao.api.services.VotacaoService;


@RestController
@RequestMapping("api/votacao")
public class VotacaoController {
	
	private static Logger log = LoggerFactory.getLogger(VotacaoController.class);
	
	//Strings para checagem de CPF
	private static final String URL_CHECK_CPF  = "https://user-info.herokuapp.com/users/";
	private static final String UNABLE_TO_VOTE = "UNABLE_TO_VOTE";
	private static final String ABLE_TO_VOTE   = "ABLE_TO_VOTE";
	
	@Autowired
	private PautaService pautaService;
	
	
	@Autowired
	private VotacaoService votacaoService;
	
	@Autowired
	private AssociadoService associadoService;
	
	
	@PostMapping
	public ResponseEntity<ResponseWrapper<VotacaoDto>> cadastrarVotoAssociado(@Valid @RequestBody VotacaoDto votacaoDto, BindingResult result){
		log.info("Cadastrando voto do associado");
		
		ResponseWrapper<VotacaoDto> responseWrapper = new ResponseWrapper<VotacaoDto>();
		validarReqsDeVotacao(votacaoDto, result);
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> responseWrapper.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(responseWrapper);
		}
		
		Associado associado = this.associadoService.localizarAssociadoPorId(votacaoDto.getAssociadoId());
		Pauta pauta = this.pautaService.localizarPautaPorId(votacaoDto.getPautaId());
		
		Votacao votacao = new Votacao();
		votacao.setAssociado(associado);
		votacao.setPauta(pauta);
		votacao.setVoto(votacaoDto.isVoto());
		
		
		votacao = this.votacaoService.receberVotoDeAssociadoEmPauta(votacao);
		
		VotacaoDto votacaoDtoConvertido = converterEntityVotacaoEmVotacaoDto(votacao);
		
		responseWrapper.setData(votacaoDtoConvertido);
		
		return ResponseEntity.ok(responseWrapper);
	}

	
	@GetMapping(value = "/contabilizar/{idPauta}")
	public ResponseEntity<ResponseWrapper<ContagemVotosDto>> contarVotosPositivos(@PathVariable("idPauta") int idPauta){

		ResponseWrapper<ContagemVotosDto> responseWrapper = new ResponseWrapper<ContagemVotosDto>();
		
		Pauta pauta = this.pautaService.localizarPautaPorId(idPauta);
		
		if(pauta == null) { //verifica se a Pauta já foi cadastrada
			responseWrapper.getErrors().add("Pauta Não cadastrada. Impossível contabilizar");
			return ResponseEntity.badRequest().body(responseWrapper);
		}
		
		if(pauta.getFim() == null) { //Verifica se a Sessão de votação já foi iniciada
			responseWrapper.getErrors().add("Pauta Ainda Não Iniciada. Impossível contabilizar");
			return ResponseEntity.badRequest().body(responseWrapper);
		}
		
		int votosPositivos = this.votacaoService.contabilizarVotosPositivosPorPauta(idPauta);
		int votosNegativos = this.votacaoService.contabilizarVotosNegativosPorPauta(idPauta);
		
		ContagemVotosDto contagemDto = new ContagemVotosDto();
		contagemDto.setIdPauta(pauta.getId());
		contagemDto.setVotosSim(votosPositivos);
		contagemDto.setVotosNao(votosNegativos);
		
		
		responseWrapper.setData(contagemDto);
		
		return ResponseEntity.ok(responseWrapper);
	}
	
	
	//----------------------- MÉTODOS DE APOIO ----------------------
	
	private void validarReqsDeVotacao(VotacaoDto votacaoDto, BindingResult result) {
		
		//--------------------------------------- VALIDAÇÕES DE PAUTA ------------------------------------------
		
		Pauta pauta = this.pautaService.localizarPautaPorId(votacaoDto.getPautaId());
		if(pauta == null) { //Testa se Pauta está cadastrada na base
			result.addError(new ObjectError("votacao", "Pauta não encontrada pelo ID " + votacaoDto.getPautaId()));
			return;
			
		}else {
			
			if(pauta.getFim() == null) { //Sessão não foi iniciada
				
				result.addError(new ObjectError("votacao", "A Pauta não foi aberta. Proibido Votação"));
				return;

			} else { //Sessão já foi iniciada, verificando se o tempo já se encerrou
				
				
				log.info("Data Abertura Pauta: {}", pauta.getInicio());
				log.info("Data Fim Pauta: {}", pauta.getFim() );
				
				if(new Date().after(pauta.getFim())) { //Verifica se ainda é permitido votar
					result.addError(new ObjectError("votacao", "Tempo de votação encerrado. Operação não permitida."));
					return;
				}
			
			}
			
		}
		
		
		//---------------------------------------- VALIDAÇÕES DE ASSOCIADO ----------------------------------------
		
		Associado associado = this.associadoService.localizarAssociadoPorId(votacaoDto.getAssociadoId());
		if(associado == null) {
			result.addError(new ObjectError("votacao", "Associado não encontrado pelo ID " + votacaoDto.getAssociadoId()));
			return;
			
		}else {
			
			//Verificando se o associado já votou na Pauta
			
			int numVotosDeAssociado = votacaoService.verificarNumeroDeVezesAssociadoVotouEmPauta(votacaoDto.getPautaId(), votacaoDto.getAssociadoId());
			if(numVotosDeAssociado > 0) {
				result.addError(new ObjectError("votacao", "Associado já votou para esta pauta. Operação negada."));
				return;
			}
			
			
			try { //Verificando o CPF do Associado
				
				RestTemplate restTemplate = new RestTemplate();
				ValidaCpfAssociadoDto validaCpfAssociadoDto = restTemplate.getForObject(URL_CHECK_CPF + associado.getCpf(), ValidaCpfAssociadoDto.class);
				
				if(validaCpfAssociadoDto.getStatus().equals(UNABLE_TO_VOTE)) {
					log.info("O CPF do Associado de ID {} é válido, porém o mesmo não é apto a votar. UNABLE_TO_VOTE", associado.getId());
					result.addError(new ObjectError("votacao",  "O CPF de número " + associado.getCpf() + " do Associado com ID " + associado.getId() + 
							" é válido, porém o mesmo não é apto a votar UNABLE_TO_VOTE. Voto não computado.") );
					return;
					
				}else if(validaCpfAssociadoDto.getStatus().equals(ABLE_TO_VOTE)) {
					log.info("O CPF do Associado de ID {} é válido e o mesmo está aptor a votar.", associado.getId());
				}
					
				
			}catch (final HttpClientErrorException e) { //Erro 404 CPF incorreto
				log.info("CPF de número {} do Associado com ID {} é inexistente. Votação negada.", associado.getCpf(), associado.getId());
				result.addError(new ObjectError("votacao",  "CPF de número " + associado.getCpf() + " do Associado com ID " + associado.getId() +" é inexistente. Votação Negada.") );
				return;
			}catch (Exception e) {
				log.info("Ocorreu uma exeção ao acessar a API de verificação de CPF");
				result.addError(new ObjectError("votaca", "Ocorreu uma exceção ao tentar acessar o serviço de verificação de CPF"));
				return;
			}
		}
		

	}
	
	private VotacaoDto converterEntityVotacaoEmVotacaoDto(Votacao votacao) {
		VotacaoDto retorno = new VotacaoDto();
		retorno.setAssociadoId(votacao.getAssociado().getId());
		retorno.setPautaId(votacao.getPauta().getId());
		retorno.setVoto(votacao.getVoto());
		return retorno;
	}

}