package com.msstudies.mscartoes.applications;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msstudies.mscartoes.applications.dtos.CartaoSaveDTO;
import com.msstudies.mscartoes.applications.dtos.CartoesPorClienteDTO;
import com.msstudies.mscartoes.applications.services.CartaoService;
import com.msstudies.mscartoes.applications.services.ClienteCartaoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {
	
	private final CartaoService cartaoService;
	
	private final ClienteCartaoService clienteCardService;
	
	
	@GetMapping
	public String status() {
		return "ok";
	}
	
	@PostMapping
	public ResponseEntity<CartaoSaveDTO> saveCard(@RequestBody CartaoSaveDTO save) {
		this.cartaoService.saveCard(save);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(params  = "renda")
	public ResponseEntity<List<CartaoSaveDTO>> getCartaoRenda(@RequestParam("renda") Long renda){
		var obj = cartaoService.getCartoesRendaMenorIgual(renda);
		return ResponseEntity.ok(obj);	
	}
	
	@GetMapping(params  = "cpf")
	public ResponseEntity<List<CartoesPorClienteDTO>> getCartoesByCliente(@RequestParam("cpf") String cpf){
		var obj = clienteCardService.getCartoesPorCliente(cpf);
		return ResponseEntity.ok(obj);
	}
	
}
