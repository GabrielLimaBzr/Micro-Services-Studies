package com.msstudies.msclientes.infra.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.msstudies.msclientes.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Cliente findByCpf(String cpf);

}
