package com.msstudies.mscartoes.infra.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msstudies.mscartoes.domain.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long>{

	List<Cartao> findByRendaLessThanEqual(BigDecimal rendaCliente);

}
