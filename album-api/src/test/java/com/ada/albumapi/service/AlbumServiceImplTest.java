package com.ada.albumapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ada.albumapi.model.dto.AlbumDTO;
import com.ada.albumapi.model.entity.Album;
import com.ada.albumapi.repository.AlbumRepository;
import com.ada.albumapi.service.Impl.AlbumServiceImpl;

import jakarta.persistence.EntityNotFoundException;

public class AlbumServiceImplTest {

	@Mock
	private AlbumRepository albumRepositoryMock;

	@Mock
	private SlotAlbumService slotAlbumServiceMock;

	@InjectMocks
	private AlbumServiceImpl albumService;

	private UUID identificador;

	private Album album;
	private AlbumDTO albumDTO;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		identificador = UUID.randomUUID();
		album = new Album();
		album.setIdentificador(UUID.randomUUID());
		album.setTitulo("Test Album");
		album.setDescricao("Test Album Description");

		albumDTO = new AlbumDTO();
		albumDTO.setId(1L);
		albumDTO.setTitulo("Test Album");
		albumDTO.setDescricao("Test Album Description");
	}

	@Test
	public void testBuscarTodos() {
		List<Album> albums = new ArrayList<>();
		Album album = new Album();
		album.setIdentificador(UUID.randomUUID());
		album.setTitulo("Test Album");
		album.setDescricao("Test Album Description");
		albums.add(album);

		when(albumRepositoryMock.findAll()).thenReturn(albums);

		List<AlbumDTO> result = albumService.buscarTodos();

		assertNotNull(result);
		assertTrue(result.size() > 0);
		assertEquals(result.get(0).getTitulo(), album.getTitulo());
		assertEquals(result.get(0).getDescricao(), album.getDescricao());
	}

	@Test
	public void testBuscarUm() {
		UUID identificador = UUID.randomUUID();
		Album album = new Album();
		album.setIdentificador(identificador);
		album.setTitulo("Test Album");
		album.setDescricao("Test Album Description");

		when(albumRepositoryMock.findByIdentificador(identificador)).thenReturn(Optional.of(album));

		AlbumDTO result = albumService.buscarUm(identificador);

		assertNotNull(result);
		assertEquals(result.getTitulo(), album.getTitulo());
		assertEquals(result.getDescricao(), album.getDescricao());
	}

	@Test
	public void testCriar() {
		AlbumDTO albumDto = new AlbumDTO();
		albumDto.setId(1L);
		albumDto.setTitulo("Test Album");
		albumDto.setDescricao("Test Album Description");

		Album album = new Album();
		album.setId(1L);
		album.setIdentificador(UUID.randomUUID());
		album.setTitulo(albumDto.getTitulo());
		album.setDescricao(albumDto.getDescricao());

		when(albumRepositoryMock.save(Mockito.any(Album.class))).thenReturn(album);

		AlbumDTO result = albumService.criar(albumDto);

		assertNotNull(result);
		assertEquals(result.getTitulo(), albumDto.getTitulo());
		assertEquals(result.getDescricao(), albumDto.getDescricao());
	}

	@Test
    public void testEditarAlbumInexistente() {
        // given
        when(albumRepositoryMock.findByIdentificador(identificador)).thenReturn(Optional.empty());

        // when/then
        assertThrows(EntityNotFoundException.class, () -> albumService.editar(identificador, albumDTO));
        verify(albumRepositoryMock, times(1)).findByIdentificador(identificador);
        verify(albumRepositoryMock, never()).save(any());
    }

	@Test
    public void testEditarAlbumExistente() {
        // given
        when(albumRepositoryMock.findByIdentificador(identificador)).thenReturn(Optional.of(album));

        when(albumRepositoryMock.save(Mockito.any(Album.class))).thenReturn(album);
        // when
        AlbumDTO result = albumService.editar(identificador, albumDTO);

        // then
        assertNotNull(result);
        assertEquals(albumDTO.getTitulo(), result.getTitulo());
        assertEquals(albumDTO.getDescricao(), result.getDescricao());
        verify(albumRepositoryMock, times(1)).findByIdentificador(identificador);
        verify(albumRepositoryMock, times(1)).save(album);
    }

	@Test
    public void testExcluirAlbumExistente() {
        // given
        when(albumRepositoryMock.findByIdentificador(identificador)).thenReturn(Optional.of(album));

        // when
        albumService.excluir(identificador);

        // then
        verify(albumRepositoryMock, times(1)).findByIdentificador(identificador);
        verify(albumRepositoryMock, times(1)).deleteById(album.getId());
    }

	@Test
    public void testExcluirAlbumInexistente() {
        // given
        when(albumRepositoryMock.findByIdentificador(identificador)).thenReturn(Optional.empty());

        // when/then
        assertThrows(EntityNotFoundException.class, () -> albumService.excluir(identificador));
        verify(albumRepositoryMock, times(1)).findByIdentificador(identificador);
        verify(albumRepositoryMock, never()).deleteById(any());
    }

}