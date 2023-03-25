package com.ada.albumapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ada.albumapi.model.entity.SlotAlbum;

public interface SlotAlbumRepository extends JpaRepository<SlotAlbum, Long> {
	
	Optional<SlotAlbum> findByIdentificador(UUID identificador);
	
	Optional<List<SlotAlbum>> findAllByIdentificadorAlbum(UUID identificadorAlbum);

}
