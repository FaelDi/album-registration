package com.ada.albumapi.model.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ada.albumapi.model.dto.SlotAlbumDTO;
import com.ada.albumapi.model.entity.SlotAlbum;

public class SlotAlbumMapper implements Mapper<SlotAlbum, SlotAlbumDTO> {

	public List<SlotAlbumDTO> parseListDTO(List<SlotAlbum> entities) {
		
		List<SlotAlbumDTO> dtoList = new ArrayList<SlotAlbumDTO>();
		
		if (entities != null && entities.size() > 0) {
			
			for( SlotAlbum album: entities) {
				
				dtoList.add(parseDTO(album));
				
			}
			
		}
		
		return dtoList;
		
	}
	
	public List<SlotAlbum> parseListEntity(List<SlotAlbumDTO> dtoList) {
		
		List<SlotAlbum> entities = new ArrayList<SlotAlbum>();
		
		if (dtoList != null && dtoList.size() > 0) {
			
			for( SlotAlbumDTO album: dtoList) {
				
				entities.add(parseEntity(album));
				
			}
			
		}
		
		return entities;
		
	}

	public SlotAlbumDTO parseDTO(SlotAlbum entity) {
		
		SlotAlbumDTO dto = new SlotAlbumDTO();
		
		dto.setId(entity.getId());
		dto.setIdentificador(entity.getIdentificador());
		dto.setIdentificadorAlbum(entity.getIdentificadorAlbum());
		dto.setQuantidadeFigurinhas(entity.getQuantidadeFigurinhas());
		dto.setOrdem(entity.getOrdem());
		
		return dto;
		
	}

	public SlotAlbum parseEntity(SlotAlbumDTO dto) {

		SlotAlbum entity = new SlotAlbum();
		
		entity.setId(dto.getId());
		entity.setIdentificador(dto.getIdentificador());
		entity.setIdentificadorAlbum(dto.getIdentificadorAlbum());
		entity.setQuantidadeFigurinhas(dto.getQuantidadeFigurinhas());
		entity.setOrdem(dto.getOrdem());
		
		return entity;
		
	}

}
