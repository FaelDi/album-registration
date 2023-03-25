package com.ada.albumapi.model.dto;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlbumDTO {

	private Long id;
	
	private UUID identificador;
	
	private String titulo;
	
	private String descricao;

}
