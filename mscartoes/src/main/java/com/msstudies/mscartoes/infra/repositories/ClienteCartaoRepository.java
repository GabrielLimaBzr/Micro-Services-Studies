package com.msstudies.mscartoes.infra.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msstudies.mscartoes.domain.ClienteCartao;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long>{
	List<ClienteCartao> findByCpf(String cpf);

}
