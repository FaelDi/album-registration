package com.ada.albumapi.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class RaridadeUtils {

	public List<Integer> generate() {
		
		List<Integer> raridades = Arrays.asList(1, 2, 2, 2, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4);
		
		Collections.shuffle(raridades);

		return raridades;
		
	}
	
}
