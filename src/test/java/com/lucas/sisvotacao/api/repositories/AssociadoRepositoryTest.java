package com.lucas.sisvotacao.api.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.lucas.sisvotacao.api.entities.Associado;

@SpringBootTest
@ActiveProfiles("test")
public class AssociadoRepositoryTest {
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	@BeforeEach
	public void setUp() {
		
		Associado associado1 = new Associado();
		associado1.setCpf("223190");
		associado1.setNome("lucas");
		
		Associado associado2 = new Associado();
		associado2.setCpf("54321");
		associado2.setNome("maiara");
		
		this.associadoRepository.save(associado1);
		this.associadoRepository.save(associado2);
		
	}
	
	@Test
	public void localizarAssociadoPorCpf() {
		Associado associado = this.associadoRepository.findAll().get(0);
		int idAssociado = associado.getId();
		
		associado = this.associadoRepository.findById(idAssociado);
		
		
		assertEquals("lucas", associado.getNome());
	}
	
	@Test
	public void listarTodosAssociados() {
		
		List<Associado> listaAssociados = this.associadoRepository.findAll();
		
		assertEquals(2, listaAssociados.size());
		
	}
	

}
