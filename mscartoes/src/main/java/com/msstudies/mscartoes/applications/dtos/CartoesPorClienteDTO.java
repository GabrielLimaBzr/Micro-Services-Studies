package com.msstudies.mscartoes.applications.dtos;

import java.math.BigDecimal;

import com.msstudies.mscartoes.domain.ClienteCartao;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartoesPorClienteDTO {
	
	private String nome;
	private String bandeira;
	private BigDecimal limiteLiberado;
	
	
	public CartoesPorClienteDTO(ClienteCartao clienteCartao) {
		super();
		this.nome = clienteCartao.getCartao().getNome();
		this.bandeira = clienteCartao.getCartao().getBandeira().toString();
		this.limiteLiberado = clienteCartao.getLimiteLiberado();
	}
	
	

}
