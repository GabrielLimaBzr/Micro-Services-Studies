package com.msstudies.msavaliacredito.application.exceptions;

import lombok.Getter;

public class ErroComunicacaoServidorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	private Integer status;

	public ErroComunicacaoServidorException(String msg, Integer status) {
		super(msg);
		this.status = status;
	}

}
