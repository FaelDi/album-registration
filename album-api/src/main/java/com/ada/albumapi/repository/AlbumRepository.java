package com.ada.albumapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ada.albumapi.model.entity.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

	Optional<Album> findByIdentificador(UUID identificador);
	
}
