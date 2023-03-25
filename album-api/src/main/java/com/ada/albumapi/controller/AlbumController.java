package com.ada.albumapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.albumapi.model.dto.AlbumDTO;
import com.ada.albumapi.service.AlbumService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/albuns")
@Slf4j
public class AlbumController extends BaseController<AlbumDTO, AlbumService> {

	public AlbumController(AlbumService service) {
		super(service);
	}

    @PostMapping
    public ResponseEntity<AlbumDTO> criar(@RequestBody @Valid AlbumDTO entidade) {
        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(service.criar(entidade));

        }catch(Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
