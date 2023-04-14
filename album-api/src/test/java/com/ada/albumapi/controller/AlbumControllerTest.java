package com.ada.albumapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.ada.albumapi.model.dto.AlbumDTO;
import com.ada.albumapi.service.AlbumService;

public class AlbumControllerTest {

	@InjectMocks
	private AlbumController controller;

	@Mock
	private AlbumService service;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCriarAlbum() {

		// Given

		AlbumDTO album = new AlbumDTO();
		album.setId(1L);
		album.setTitulo("Test Album");
		album.setDescricao("Test Album Description");

		when(service.criar(album)).thenReturn(album);

		// When
		ResponseEntity<AlbumDTO> response = controller.criar(album);

		// Then
		verify(service).criar(album);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
		assertEquals(album, response.getBody());
	}

	@Test
	public void testCriarAlbumComErro() {

		// Given
		AlbumDTO album = new AlbumDTO();
		album.setId(1L);
		album.setTitulo("Test Album");
		album.setDescricao("Test Album Description");

		when(service.criar(album)).thenThrow(new RuntimeException());

		// When
		ResponseEntity<AlbumDTO> response = controller.criar(album);

		// Then
		verify(service).criar(album);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
}
