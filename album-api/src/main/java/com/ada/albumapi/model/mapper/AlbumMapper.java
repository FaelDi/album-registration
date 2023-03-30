package com.ada.albumapi.model.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ada.albumapi.model.dto.AlbumDTO;
import com.ada.albumapi.model.entity.Album;

public class AlbumMapper implements Mapper<Album, AlbumDTO> {

	public List<AlbumDTO> parseListDTO(List<Album> entities) {
		
		List<AlbumDTO> dtoList = new ArrayList<AlbumDTO>();
		
		if (entities != null && entities.size() > 0) {
			
			for( Album album: entities) {
				
				dtoList.add(parseDTO(album));
				
			}
			
		}
		
		return dtoList;
		
	}
	
	public List<Album> parseListEntity(List<AlbumDTO> dtoList) {
		
		List<Album> entities = new ArrayList<Album>();
		
		if (dtoList != null && dtoList.size() > 0) {
			
			for( AlbumDTO album: dtoList) {
				
				entities.add(parseEntity(album));
				
			}
			
		}
		
		return entities;
		
	}

	public AlbumDTO parseDTO(Album entity) {
		
		AlbumDTO dto = new AlbumDTO();
		
		dto.setId(entity.getId());
		dto.setUsuarioId(entity.getUsuarioId());
		dto.setIdentificador(entity.getIdentificador());
		dto.setIdentificadorFixo(entity.getIdentificadorFixo());
		dto.setTitulo(entity.getTitulo());
		dto.setDescricao(entity.getDescricao());
		
		return dto;
		
	}

	public Album parseEntity(AlbumDTO dto) {

		Album entity = new Album();
		
		entity.setId(dto.getId());
		entity.setUsuarioId(dto.getUsuarioId());
		entity.setIdentificador(dto.getIdentificador());
		entity.setIdentificadorFixo(dto.getIdentificadorFixo());
		entity.setTitulo(dto.getTitulo());
		entity.setDescricao(dto.getDescricao());
		
		return entity;
		
	}

}
