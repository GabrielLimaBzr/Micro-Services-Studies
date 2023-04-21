package com.msstudies.msclientes.domain.exceptions;

public class ClienteNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

		public ClienteNotFoundException(String cpf) {
	        super("Cliente não encontrado com o cpf " + cpf);
	    }
	
}
