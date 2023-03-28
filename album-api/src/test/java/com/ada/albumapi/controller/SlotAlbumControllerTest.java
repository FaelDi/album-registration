package com.ada.albumapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.ada.albumapi.model.dto.SlotAlbumDTO;
import com.ada.albumapi.service.SlotAlbumService;

import jakarta.persistence.EntityNotFoundException;

public class SlotAlbumControllerTest {

	private SlotAlbumController controller;

	@Mock
	private SlotAlbumService service;

	private SlotAlbumDTO slotAlbumDTO;
	private UUID identificador;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		controller = new SlotAlbumController(service);

		identificador = UUID.randomUUID();

		slotAlbumDTO = new SlotAlbumDTO();
		slotAlbumDTO.setIdentificadorAlbum(UUID.randomUUID());
		slotAlbumDTO.setQuantidadeFigurinhas(10);
		slotAlbumDTO.setRaridade(3);
	}

	@Test
	public void buscarTodosPorAlbum_deveRetornarOk_quandoEncontrarSlotsPorAlbum() throws Exception {
		List<SlotAlbumDTO> slots = new ArrayList<>();
		slots.add(slotAlbumDTO);

		when(service.buscarTodosPorAlbum(any(UUID.class))).thenReturn(slots);

		ResponseEntity<List<SlotAlbumDTO>> response = controller.buscarTodosPorAlbum(identificador);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertThat(response.getBody()).isEqualTo(slots);
	}

	@Test
	public void buscarTodosPorAlbum_deveRetornarNoContent_quandoNaoEncontrarSlotsPorAlbum() throws Exception {
		when(service.buscarTodosPorAlbum(any(UUID.class))).thenThrow(EntityNotFoundException.class);

		ResponseEntity<List<SlotAlbumDTO>> response = controller.buscarTodosPorAlbum(identificador);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}


	@Test
	public void criar_deveRetornarCreated_quandoCriarTodosSlots() throws Exception {
		List<SlotAlbumDTO> slots = new ArrayList<>();
		slots.add(slotAlbumDTO);

		when(service.criarTodosSlots(any(UUID.class))).thenReturn(slots);

		ResponseEntity<List<SlotAlbumDTO>> response = controller.criar(identificador);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
		assertThat(response.getBody()).isEqualTo(slots);
	}

}
