package com.ada.albumapi.service;

import java.util.List;
import java.util.UUID;

public interface BaseService<T> {
	
	List<T> buscarTodos();
	T buscarUm(UUID identificador);
	T criar(T dto);
	T editar(UUID identificador, T dto);
	void excluir(UUID identificador);
}
