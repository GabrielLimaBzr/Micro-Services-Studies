package com.msstudies.msavaliacredito.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msstudies.msavaliacredito.application.exceptions.DadosClienteNotFoundException;
import com.msstudies.msavaliacredito.application.exceptions.ErroComunicacaoServidorException;
import com.msstudies.msavaliacredito.application.exceptions.ErroSolicitacaoCartaoException;
import com.msstudies.msavaliacredito.application.services.AvaliaCreditoService;
import com.msstudies.msavaliacredito.domain.model.DadosAvaliacao;
import com.msstudies.msavaliacredito.domain.model.DadosSolicitacaoCartao;
import com.msstudies.msavaliacredito.domain.model.ProtocoloSolicitacaoCartao;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("avalia-credito")
@RequiredArgsConstructor
public class AvaliaCreditoController {

	private final AvaliaCreditoService service;

	@GetMapping
	public String status() {
		return "ok";
	}

	@GetMapping(value = "situacao-cliente")
	public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
		try {

			var obj = service.obterSituacaoCliente(cpf);
			return ResponseEntity.ok(obj);

		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoServidorException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados) {
		try {
			var obj = service.realizarAvaliacao(dados.getCpf(), dados.getRenda());
			return ResponseEntity.ok(obj);

		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoServidorException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}
	
	@PostMapping("solicitacao-cartao")
	public ResponseEntity solicitaCartao(@RequestBody DadosSolicitacaoCartao dados) {
		try {
			ProtocoloSolicitacaoCartao protocolo = service.solicitarEmissaoCartao(dados);
			return ResponseEntity.ok(protocolo);
		} catch (ErroSolicitacaoCartaoException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
