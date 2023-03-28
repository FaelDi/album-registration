package com.ada.albumapi.model.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlbumDTO {

	private Long id;
	
	private UUID identificador;
	
	private UUID identificadorFixo;
	
	private String titulo;
	
	private String descricao;

}
