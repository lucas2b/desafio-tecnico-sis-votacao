package com.lucas.sisvotacao.api.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.sisvotacao.api.dto.AberturaDeVotacaoDto;
import com.lucas.sisvotacao.api.dto.CadastroDePautaDto;
import com.lucas.sisvotacao.api.dto.ListagemPautaDto;
import com.lucas.sisvotacao.api.entities.Pauta;
import com.lucas.sisvotacao.api.response.ResponseWrapper;
import com.lucas.sisvotacao.api.services.PautaService;

@RestController
@RequestMapping("api/pauta")
@CrossOrigin(origins = "*")
public class PautaController {
	
	private static Logger log = LoggerFactory.getLogger(PautaController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private PautaService pautaService;
	
	public PautaController() {
		
	}
	
	@PostMapping
	public ResponseEntity<ResponseWrapper<CadastroDePautaDto>> criarNovaPauta(@Valid @RequestBody CadastroDePautaDto pautaDto, BindingResult result) 
			throws ParseException{
		
		log.info("Criando nova pauta: {}", pautaDto);
		
		ResponseWrapper<CadastroDePautaDto> responseWrapper = new ResponseWrapper<CadastroDePautaDto>();
		
		Pauta pauta = this.pautaService.cadastrarNovaPauta(converteCadastroDePautaDtoEmEntityPauta(pautaDto));
		
		responseWrapper.setData(converterEntityPautaEmCadastroDePautaDto(pauta));

		return ResponseEntity.ok(responseWrapper);
	}
	
	@PutMapping
	public ResponseEntity<ResponseWrapper<AberturaDeVotacaoDto>> abrirSessaoDeVotacao(@Valid @RequestBody AberturaDeVotacaoDto aberturaDeVotacaoDto, BindingResult result) 
			throws ParseException{
		
		log.info("Abrindo sessão de Votação da Pauta: {}", aberturaDeVotacaoDto);
		
		String descricao = validaExistenciaDePauta(aberturaDeVotacaoDto, result);
		
		ResponseWrapper<AberturaDeVotacaoDto> responseWrapper = new ResponseWrapper<AberturaDeVotacaoDto>();
		
		if(result.hasErrors()) {
			log.error("Erro ao buscar pauta na base de dados");
			result.getAllErrors().forEach(error -> responseWrapper.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(responseWrapper);
		}
		
		Pauta pauta = converterAberturaDeVotacaoDtoEmEntityPauta(aberturaDeVotacaoDto);
		pauta.setDescricao(descricao);
		pauta = this.pautaService.abrirSessaoDeVotacaoEmPauta(pauta); //Atualizando a Pauta e trazendo a atualizada
		
		responseWrapper.setData(converterEntityPautaEmAberturaDeVotacaoDto(pauta));
		
		return ResponseEntity.ok(responseWrapper);
	}
	
	@GetMapping("/listarPautas")
	public ResponseEntity<ResponseWrapper<List<ListagemPautaDto>>> listarPautas(){
		
		List<Pauta> listaPautas = this.pautaService.listarTodasPautas();
		
		ResponseWrapper<List<ListagemPautaDto>> responseWrapper = new ResponseWrapper<List<ListagemPautaDto>>();
		
		List<ListagemPautaDto> listagemPautasDto = new ArrayList<ListagemPautaDto>();
		
		listaPautas.forEach(pauta -> listagemPautasDto.add(converterEntityPautaEmListagemPautaDto(pauta)));
		
		responseWrapper.setData(listagemPautasDto);
		
		return ResponseEntity.ok(responseWrapper);
	}
	
	
	//------------------------ MÉTODOS DE APOIO --------------------
	
	private Pauta converteCadastroDePautaDtoEmEntityPauta(CadastroDePautaDto pautaDto) throws ParseException {
		Pauta pauta = new Pauta();
		pauta.setDescricao(pautaDto.getDescricao());
		return pauta;
	}
	
	private Pauta converterAberturaDeVotacaoDtoEmEntityPauta(AberturaDeVotacaoDto aberturaDeVotacaoDto) {
		Pauta pauta = new Pauta();
		
		pauta.setId(aberturaDeVotacaoDto.getId());
		pauta.setInicio(new Date());
		
		if(aberturaDeVotacaoDto.getTempoDeSessaoMinutos() == null)
			pauta.setFim(DateUtils.addMinutes(new Date(), 1));
		else
			pauta.setFim(DateUtils.addMinutes(new Date(), aberturaDeVotacaoDto.getTempoDeSessaoMinutos()));
		
		
		return pauta;
		
	}
	
	private CadastroDePautaDto converterEntityPautaEmCadastroDePautaDto(Pauta pauta) {
		CadastroDePautaDto pautaDto = new CadastroDePautaDto();
		pautaDto.setId(pauta.getId());
		pautaDto.setDescricao(pauta.getDescricao());		
		return pautaDto;
	}
	
	private AberturaDeVotacaoDto converterEntityPautaEmAberturaDeVotacaoDto(Pauta pauta) throws ParseException {
		AberturaDeVotacaoDto aberturaDeVotacaoDto = new AberturaDeVotacaoDto();
		aberturaDeVotacaoDto.setId(pauta.getId());
		aberturaDeVotacaoDto.setDataInicioSessao(this.dateFormat.format(pauta.getInicio()));
		aberturaDeVotacaoDto.setDataFimSessao(this.dateFormat.format(pauta.getFim()));
		
		return aberturaDeVotacaoDto;
	}
	
	
	private String validaExistenciaDePauta(AberturaDeVotacaoDto aberturaDeVotacaoDto, BindingResult result) {
		
		Pauta pauta = this.pautaService.localizarPautaPorId(aberturaDeVotacaoDto.getId());
		
		if(pauta == null) {
			result.addError(new ObjectError("pauta", "Pauta não encontrada para o Id " + aberturaDeVotacaoDto.getId()));
			return null;
		}else {
			return pauta.getDescricao();
		}
		
	}
	
	private ListagemPautaDto converterEntityPautaEmListagemPautaDto(Pauta pauta) {
		ListagemPautaDto listagemPautaDto = new ListagemPautaDto();
		listagemPautaDto.setId(pauta.getId());
		listagemPautaDto.setDescricao(pauta.getDescricao());
		
		if (pauta.getInicio() != null) {
			listagemPautaDto.setInicio(this.dateFormat.format(pauta.getInicio()));
		}
		
		if(pauta.getFim() != null) {
			listagemPautaDto.setFim(this.dateFormat.format(pauta.getFim()));
		}
		
		return listagemPautaDto;
	}

}
