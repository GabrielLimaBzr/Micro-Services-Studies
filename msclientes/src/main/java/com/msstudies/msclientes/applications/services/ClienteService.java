package com.msstudies.msclientes.applications.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msstudies.msclientes.domain.Cliente;
import com.msstudies.msclientes.infra.repositories.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

	private final ClienteRepository repository;

	@Transactional
	public Cliente save(Cliente cliente) {
		return repository.save(cliente);
	}

	public Cliente getByCpf(String cpf) {

		return repository.findByCpf(cpf);
	}

}
