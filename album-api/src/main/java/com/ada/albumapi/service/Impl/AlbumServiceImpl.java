package com.ada.albumapi.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ada.albumapi.model.dto.AlbumDTO;
import com.ada.albumapi.model.entity.Album;
import com.ada.albumapi.model.mapper.AlbumMapper;
import com.ada.albumapi.repository.AlbumRepository;
import com.ada.albumapi.service.AlbumService;
import com.ada.albumapi.service.SlotAlbumService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AlbumServiceImpl implements AlbumService {

	private AlbumRepository repository;
	
	private AlbumMapper mapper = new AlbumMapper();
	
	private SlotAlbumService slotAlbumService;
	
	public AlbumServiceImpl(AlbumRepository repository, SlotAlbumService slotAlbumService) {
		this.repository = repository;
		this.slotAlbumService = slotAlbumService;
	}
	
	@Override
	public List<AlbumDTO> buscarTodos() {

		return mapper.parseListDTO(repository.findAll());
		
	}

	@Override
	public AlbumDTO buscarUm(UUID identificador) {

		Optional<Album> albumOp = repository.findByIdentificador(identificador);
		
		if (albumOp.isPresent()) {
			
			Album album = albumOp.get();
			
			return mapper.parseDTO(album);
			
		}
		
		throw new EntityNotFoundException();
	}

	@Override
	public AlbumDTO criar(AlbumDTO dto) {

		Album album = mapper.parseEntity(dto);
		
		album.setId(null); 
		album.setIdentificador(UUID.randomUUID());
		
		if (album.getIdentificadorFixo() == null) {
			
			album.setIdentificadorFixo(album.getIdentificador());
			
		}
		
		Album entity = repository.save(album);
		
		if (entity != null) {
			
			slotAlbumService.criarTodosSlots(entity.getIdentificador(), 20);
			
		}
		
		return mapper.parseDTO(entity);

	}

	@Override
	public AlbumDTO editar(UUID identificador, AlbumDTO dto) {

		Optional<Album> albumOp = repository.findByIdentificador(identificador);
		
		if (albumOp.isPresent()) {
			
			Album album = albumOp.get();
			
			album.setTitulo(dto.getTitulo());
			album.setDescricao(dto.getDescricao());
			
			album = repository.save(album);
			
			return mapper.parseDTO(album);
			
		}
		
		throw new EntityNotFoundException();
		
	}

	@Override
	public void excluir(UUID identificador) {

		Optional<Album> albumOp = repository.findByIdentificador(identificador);
		
		if (!albumOp.isPresent()) {
			
			throw new EntityNotFoundException();
			
		}
		
		Album album = albumOp.get();
		
		repository.deleteById(album.getId());
		
	}

}
