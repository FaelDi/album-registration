package com.ada.albumapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.albumapi.model.dto.SlotAlbumDTO;
import com.ada.albumapi.service.SlotAlbumService;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/slots")
@Slf4j
public class SlotAlbumController extends BaseController<SlotAlbumDTO, SlotAlbumService> {

	public SlotAlbumController(SlotAlbumService service) {
		super(service);
	}


    @GetMapping("/album/{identificador}")
    public ResponseEntity<List<SlotAlbumDTO>> buscarTodosPorAlbum(@PathVariable("identificador") UUID identificador) {
        try {
        	
        	return ResponseEntity.ok(service.buscarTodosPorAlbum(identificador));
        	
        } catch(EntityNotFoundException ex) {
        	
        	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        	
        } catch(Exception ex) {
        	
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/album/{identificador}")
    public ResponseEntity<List<SlotAlbumDTO>> criar(@PathVariable("identificador") UUID identificador) {
        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(service.criarTodosSlots(identificador, 20));

        }catch(Exception ex) {
        	
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            
        }
    }

	
}
