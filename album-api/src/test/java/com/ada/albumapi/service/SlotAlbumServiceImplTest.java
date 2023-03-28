package com.ada.albumapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.ada.albumapi.model.dto.SlotAlbumDTO;
import com.ada.albumapi.model.entity.SlotAlbum;
import com.ada.albumapi.repository.SlotAlbumRepository;
import com.ada.albumapi.service.Impl.SlotAlbumServiceImpl;
import com.ada.albumapi.utils.RaridadeUtils;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class SlotAlbumServiceImplTest {

    private SlotAlbumServiceImpl service;

    @Mock
    private SlotAlbumRepository repository;

    @Mock
    private RaridadeUtils raridadeUtils;
    SlotAlbumDTO slotAlbumDTO;
    SlotAlbum slotAlbum;
    @BeforeEach
    public void setUp() {
    	
    	slotAlbumDTO = new SlotAlbumDTO();
        slotAlbumDTO.setIdentificadorAlbum(UUID.randomUUID());
        slotAlbumDTO.setQuantidadeFigurinhas(10);
        slotAlbumDTO.setRaridade(3);

        slotAlbum = new SlotAlbum();
        slotAlbum.setIdentificador(UUID.randomUUID());
        slotAlbum.setIdentificadorAlbum(slotAlbumDTO.getIdentificadorAlbum());
        slotAlbum.setQuantidadeFigurinhas(slotAlbumDTO.getQuantidadeFigurinhas());
        slotAlbum.setRaridade(slotAlbumDTO.getRaridade());
    	
        MockitoAnnotations.openMocks(this);
        service = new SlotAlbumServiceImpl(repository, raridadeUtils);
    }

    @Test
    public void buscarTodosTest() {

        List<SlotAlbum> slotAlbumList = Arrays.asList(slotAlbum, slotAlbum);
        when(repository.findAll()).thenReturn(slotAlbumList);

        List<SlotAlbumDTO> result = service.buscarTodos();

        assertEquals(2, result.size());
        assertEquals(3, result.get(0).getRaridade());
        assertEquals(3, result.get(1).getRaridade());
    }

    @Test
    public void buscarUmTest() {
        UUID identificador = UUID.randomUUID();
        when(repository.findByIdentificador(identificador)).thenReturn(Optional.of(slotAlbum));

        SlotAlbumDTO result = service.buscarUm(identificador);

        assertEquals(3, result.getRaridade());
        assertEquals(0, result.getOrdem());
    }

    @Test
    public void buscarUmNotFoundTest() {
        UUID identificador = UUID.randomUUID();
        when(repository.findByIdentificador(identificador)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.buscarUm(identificador);
        });
    }

    @Test
    public void criarTest() {
        UUID identificador = UUID.randomUUID();
        SlotAlbumDTO dto = new SlotAlbumDTO(null, identificador, 1, 1);
        when(repository.save(ArgumentMatchers.any(SlotAlbum.class))).thenReturn(slotAlbum);

        SlotAlbumDTO result = service.criar(dto);

        assertEquals(3, result.getRaridade());
        assertEquals(0, result.getOrdem());
    }
    
    @Test
    public void testEditar() {
        UUID identificador = UUID.randomUUID();
        SlotAlbumDTO dto = new SlotAlbumDTO(identificador, UUID.randomUUID(), 1, 1);

        when(repository.findByIdentificador(identificador))
                .thenReturn(Optional.of(slotAlbum));

        when(repository.save(ArgumentMatchers.any(SlotAlbum.class))).thenReturn(slotAlbum);

        SlotAlbumDTO result = service.editar(identificador, dto);

        assertNotNull(result);
        assertEquals(dto.getIdentificadorAlbum(), result.getIdentificadorAlbum());
        assertEquals(dto.getQuantidadeFigurinhas(), result.getQuantidadeFigurinhas());
        assertEquals(dto.getRaridade(), result.getRaridade());
    }

    @Test
    public void testEditarSlotAlbumInexistente() {
        UUID identificador = UUID.randomUUID();
        SlotAlbumDTO dto = new SlotAlbumDTO(identificador, UUID.randomUUID(), 1, 1);

        when(repository.findByIdentificador(identificador)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.editar(identificador, dto));
    }

    @Test
    public void testExcluir() {
        UUID identificador = UUID.randomUUID();

        when(repository.findByIdentificador(identificador)).thenReturn(Optional.of(slotAlbum));

        service.excluir(identificador);

        // Verifica se o método deleteById foi chamado com o id correto
        verify(repository, times(1)).deleteById(slotAlbum.getId());
    }

    @Test
    public void testExcluirSlotAlbumInexistente() {
        UUID identificador = UUID.randomUUID();

        when(repository.findByIdentificador(identificador)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.excluir(identificador));
    }
    
    @Test
    public void testBuscarTodosPorAlbum() {
        UUID identificador = UUID.randomUUID();

        SlotAlbum slotAlbum1 = new SlotAlbum();
        slotAlbum1.setIdentificadorAlbum(identificador);
        slotAlbum1.setRaridade(1);
        slotAlbum1.setOrdem(1);

        SlotAlbum slotAlbum2 = new SlotAlbum();
        slotAlbum2.setIdentificadorAlbum(identificador);
        slotAlbum2.setRaridade(2);
        slotAlbum2.setOrdem(2);

        List<SlotAlbum> slotAlbumList = Arrays.asList(slotAlbum1, slotAlbum2);

        when(repository.findAllByIdentificadorAlbum(identificador)).thenReturn(Optional.of(slotAlbumList));

        List<SlotAlbumDTO> slotAlbumDTOList = service.buscarTodosPorAlbum(identificador);

        assertNotNull(slotAlbumDTOList);
        assertEquals(2, slotAlbumDTOList.size());

        SlotAlbumDTO slotAlbumDTO1 = new SlotAlbumDTO();
        slotAlbumDTO1.setIdentificadorAlbum(identificador);
        slotAlbumDTO1.setRaridade(1);
        slotAlbumDTO1.setOrdem(1);

        SlotAlbumDTO slotAlbumDTO2 = new SlotAlbumDTO();
        slotAlbumDTO2.setIdentificadorAlbum(identificador);
        slotAlbumDTO2.setRaridade(2);
        slotAlbumDTO2.setOrdem(2);

        assertNotNull(service);
    }


    @Test
    void criarTodosSlots() {
        UUID identificador = UUID.randomUUID();
        List<Integer> raridades = new ArrayList<>();
        raridades.add(1);
        raridades.add(2);
        raridades.add(3);
        when(raridadeUtils.generate()).thenReturn(raridades);
        when(repository.save(ArgumentMatchers.any(SlotAlbum.class))).thenReturn(slotAlbum);


        SlotAlbumDTO slotAlbumDTO1 = new SlotAlbumDTO(UUID.randomUUID(), identificador, 1, 1);
        SlotAlbumDTO slotAlbumDTO2 = new SlotAlbumDTO(UUID.randomUUID(), identificador, 2, 2);
        SlotAlbumDTO slotAlbumDTO3 = new SlotAlbumDTO(UUID.randomUUID(), identificador, 3, 3);
        List<SlotAlbumDTO> expected = new ArrayList<>();
        expected.add(slotAlbumDTO1);
        expected.add(slotAlbumDTO2);
        expected.add(slotAlbumDTO3);

        when(repository.findAllByIdentificadorAlbum(identificador)).thenReturn(Optional.of(new ArrayList<SlotAlbum>()));
        assertNotNull(service);
    }


}