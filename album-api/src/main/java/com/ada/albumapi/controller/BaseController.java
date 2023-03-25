package com.ada.albumapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ada.albumapi.service.BaseService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseController<T,S extends BaseService<T>> {
	
	protected S service;
	
	public BaseController(S service) {
		this.service = service;
	}

    @GetMapping
    public ResponseEntity<List<T>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/{identificador}")
    public ResponseEntity<T> buscarUm(@PathVariable("identificador") UUID identificador) {
        try {
        	
        	return ResponseEntity.ok(service.buscarUm(identificador));
        	
        }catch(EntityNotFoundException ex) {
        	
        	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch(Exception ex) {
        	
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

//    @PostMapping
//    public ResponseEntity<T> criar(@RequestBody @Valid T entidade) {
//        try {
//
//            return ResponseEntity
//                    .status(HttpStatus.CREATED)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(service.criar(entidade));
//
//        }catch(Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }

    @PutMapping("/{identificador}")
    public ResponseEntity<T> editar(@PathVariable("identificador") UUID identificador,
                                    @RequestBody @Valid T entidade) {
        try {

        	return ResponseEntity.ok(service.editar(identificador, entidade));

        }catch(EntityNotFoundException ex) {
        	
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(Exception ex) {
        	
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{identificador}")
    public ResponseEntity<Object> remover(@PathVariable("identificador") UUID identificador) {
        try {
        	
        	service.excluir(identificador);
        	return ResponseEntity.status(HttpStatus.OK).build();
        	
        }catch(EntityNotFoundException ex) {
        	
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(Exception ex) {
        	
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
