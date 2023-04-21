package com.msstudies.msavaliacredito.application.exceptions;

public class DadosClienteNotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DadosClienteNotFoundException() {
		super("Dados do Cliente não encontrado para o cpf informado.");
	}

}
