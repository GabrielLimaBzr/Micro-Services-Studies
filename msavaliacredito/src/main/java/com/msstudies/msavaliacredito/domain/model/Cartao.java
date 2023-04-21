package com.msstudies.msavaliacredito.domain.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Cartao {
	
	private String nome;
	private String bandeira;
	private BigDecimal limiteBasico; 

}
