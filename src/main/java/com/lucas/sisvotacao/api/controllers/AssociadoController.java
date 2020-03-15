package com.lucas.sisvotacao.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.sisvotacao.api.dto.AssociadoDto;
import com.lucas.sisvotacao.api.entities.Associado;
import com.lucas.sisvotacao.api.response.ResponseWrapper;
import com.lucas.sisvotacao.api.services.AssociadoService;

@RestController
@RequestMapping("api/associados")
@CrossOrigin(origins = "*")
public class AssociadoController {
	
	private static final Logger log = LoggerFactory.getLogger(AssociadoController.class);
	
	@Autowired
	private AssociadoService associadoService;
	
	
	@GetMapping
	public ResponseEntity<ResponseWrapper<List<AssociadoDto>>> listarAssociados(){
		List<Associado> listaAssociados = this.associadoService.listarTodosAssociados();
		
		ResponseWrapper<List<AssociadoDto>> responseWrapper = new ResponseWrapper<List<AssociadoDto>>();
		
		List<AssociadoDto> listaAssociadoDto = new ArrayList<AssociadoDto>();
		
		
		this.associadoService.listarTodosAssociados().forEach(associado -> listaAssociadoDto.add(conversorAssociadoEntityEmAssociadoDto(associado)));
		
		responseWrapper.setData(listaAssociadoDto);
		
		return ResponseEntity.ok(responseWrapper);	
		
	}
	
	private AssociadoDto conversorAssociadoEntityEmAssociadoDto(Associado associado) {
		AssociadoDto associadoDto = new AssociadoDto();
		associadoDto.setId(associado.getId());
		associadoDto.setNome(associado.getNome());
		associadoDto.setCpf(associado.getCpf());
		
		return associadoDto;
	}

}
