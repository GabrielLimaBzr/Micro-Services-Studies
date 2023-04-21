package com.msstudies.mscartoes.applications.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.msstudies.mscartoes.applications.dtos.CartaoSaveDTO;
import com.msstudies.mscartoes.domain.Cartao;
import com.msstudies.mscartoes.infra.repositories.CartaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartaoService {
	
	private final CartaoRepository repository;
	
	private final ModelMapper model;
	
	@Transactional
	public CartaoSaveDTO saveCard(CartaoSaveDTO cartao) {
		Cartao saveCard = repository.save(model.map(cartao, Cartao.class));
		return model.map(saveCard, CartaoSaveDTO.class);
	}
	
	public List<CartaoSaveDTO> getCartoesRendaMenorIgual(Long renda){
		var rendaCliente  = BigDecimal.valueOf(renda);
		var obj = repository.findByRendaLessThanEqual(rendaCliente);
		return obj.stream().map(x -> model.map(x, CartaoSaveDTO.class)).collect(Collectors.toList());
	}

}
