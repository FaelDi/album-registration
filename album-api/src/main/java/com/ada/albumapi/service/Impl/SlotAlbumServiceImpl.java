package com.ada.albumapi.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ada.albumapi.infrastructure.eventproducer.AlbumProducer;
import com.ada.albumapi.model.dto.SlotAlbumDTO;
import com.ada.albumapi.model.entity.SlotAlbum;
import com.ada.albumapi.model.mapper.SlotAlbumMapper;
import com.ada.albumapi.repository.SlotAlbumRepository;
import com.ada.albumapi.service.SlotAlbumService;
import com.ada.albumapi.utils.RaridadeUtils;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SlotAlbumServiceImpl implements SlotAlbumService {

	private SlotAlbumRepository repository;
	
	private SlotAlbumMapper mapper = new SlotAlbumMapper();
	
	private RaridadeUtils raridadeUtils;
	
	private AlbumProducer albumProducer;
	
	public SlotAlbumServiceImpl(SlotAlbumRepository repository, RaridadeUtils raridadeUtils, AlbumProducer albumProducer) {
		
		this.repository = repository;
		this.raridadeUtils = raridadeUtils;
		this.albumProducer = albumProducer;
		
	}
	
	@Override
	public List<SlotAlbumDTO> buscarTodos() {
		
		return mapper.parseListDTO(repository.findAll());
		
	}

	@Override
	public SlotAlbumDTO buscarUm(UUID identificador) {

		Optional<SlotAlbum> slotAlbumOp = repository.findByIdentificador(identificador);
		
		if (slotAlbumOp.isPresent()) {

			SlotAlbum slotAlbum = slotAlbumOp.get();
			
			return mapper.parseDTO(slotAlbum);
			
		}
		
		throw new EntityNotFoundException();
		
	}

	@Override
	public SlotAlbumDTO criar(SlotAlbumDTO dto) {

		SlotAlbum slotAlbum = mapper.parseEntity(dto);
		
		slotAlbum.setId(null);
		slotAlbum.setIdentificador(UUID.randomUUID());
		System.out.print(slotAlbum.getIdentificadorAlbum());
		slotAlbum = repository.save(slotAlbum);
		
		return mapper.parseDTO(slotAlbum);
		
	}

	@Override
	public SlotAlbumDTO editar(UUID identificador, SlotAlbumDTO dto) {

		Optional<SlotAlbum> slotAlbumOp = repository.findByIdentificador(identificador);
		
		if (slotAlbumOp.isPresent()) {
			
			SlotAlbum slotAlbum = slotAlbumOp.get();
			
			slotAlbum.setIdentificadorAlbum(dto.getIdentificadorAlbum());
			slotAlbum.setQuantidadeFigurinhas(dto.getQuantidadeFigurinhas());
			slotAlbum.setRaridade(dto.getRaridade());
			
			slotAlbum = repository.save(slotAlbum);
			
			return mapper.parseDTO(slotAlbum);
			
		}
		
		throw new EntityNotFoundException();
		
	}

	@Override
	public void excluir(UUID identificador) {

		Optional<SlotAlbum> slotAlbumOp = repository.findByIdentificador(identificador);
		
		if (!slotAlbumOp.isPresent()) {
			
			throw new EntityNotFoundException();
			
		}
		
		SlotAlbum slotAlbum = slotAlbumOp.get();
		
		repository.deleteById(slotAlbum.getId());
		
	}

	@Override
	public List<SlotAlbumDTO> buscarTodosPorAlbum(UUID identificador) {

		Optional<List<SlotAlbum>> slotAlbumListOp = repository.findAllByIdentificadorAlbum(identificador);
		
		if (slotAlbumListOp.isPresent()) {
			
			List<SlotAlbum> slotAlbumList = slotAlbumListOp.get();
			
			return mapper.parseListDTO(slotAlbumList);
		
		}
		
		throw new EntityNotFoundException();
		
	}

	@Override
	public List<SlotAlbumDTO> criarTodosSlots(UUID identificador) {

		int ordem = 1;
			
		for (Integer raridade: raridadeUtils.generate()) {
				
			SlotAlbumDTO slotAlbumDTO = new SlotAlbumDTO(UUID.randomUUID(), identificador, raridade, ordem);
				
			criar(slotAlbumDTO);
				
			ordem++;
		}
		
		Optional<List<SlotAlbum>> slotAlbumListOp = repository.findAllByIdentificadorAlbum(identificador);
		
		List<SlotAlbum> slotAlbumList = null;
		
		if (slotAlbumListOp.isPresent()) {
			slotAlbumList = slotAlbumListOp.get();
		}
		
		String message = identificador.toString();
		albumProducer.send(message);
			
		return mapper.parseListDTO(slotAlbumList);

	}


}
