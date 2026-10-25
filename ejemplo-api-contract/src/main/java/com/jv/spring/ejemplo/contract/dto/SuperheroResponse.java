package com.jv.spring.ejemplo.contract.dto;

import java.util.List;

public record SuperheroResponse(
		List<SuperheroDto> content,
		int pageNo,
		int pageSize,
		long totalElements,
		int totalPages,
		boolean last
) {}
