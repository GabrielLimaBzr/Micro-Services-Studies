package com.msstudies.mscartoes.applications.dtos;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartaoSaveDTO {

	private String nome;
	private String bandeira;
	private BigDecimal renda;
	private BigDecimal limiteBasico;

}
