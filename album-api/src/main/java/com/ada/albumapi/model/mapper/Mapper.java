package com.ada.albumapi.model.mapper;

import java.util.List;

public interface Mapper<T, D> {

	List<D> parseListDTO(List<T> entities);
	
	List<T> parseListEntity(List<D> dtoList);

	D parseDTO(T entity);

	T parseEntity(D dto);

}
