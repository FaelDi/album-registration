package com.ada.albumapi.model.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotAlbum {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(name="identificador", nullable=false)
	private UUID identificador;
	
	@Column(name="identificador_album", nullable=true)
	private UUID identificadorAlbum;
	
	@Column(name="quantidade_figurinhas", nullable=false)
	private int quantidadeFigurinhas;
	
	@Column(name="raridade", nullable=false, columnDefinition="INT")
	private int raridade;
	
	@Column(name="ordem", nullable=false)
	private int ordem;
}
