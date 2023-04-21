package com.msstudies.mscartoes.applications.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.msstudies.mscartoes.applications.dtos.CartoesPorClienteDTO;
import com.msstudies.mscartoes.infra.repositories.ClienteCartaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

	private final ClienteCartaoRepository repository;
	
	public List<CartoesPorClienteDTO> getCartoesPorCliente(String cpf) {
		var obj = repository.findByCpf(cpf);
		return obj.stream().map(x -> new CartoesPorClienteDTO(x)).collect(Collectors.toList());
	}
}
