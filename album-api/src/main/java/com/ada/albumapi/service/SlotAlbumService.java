package com.ada.albumapi.service;

import java.util.List;
import java.util.UUID;

import com.ada.albumapi.model.dto.SlotAlbumDTO;

public interface SlotAlbumService extends BaseService<SlotAlbumDTO> {

	List<SlotAlbumDTO> buscarTodosPorAlbum(UUID identificador);
	
	List<SlotAlbumDTO> criarTodosSlots(UUID identificador, Integer quantidade);

}
