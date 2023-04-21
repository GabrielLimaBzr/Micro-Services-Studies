package com.msstudies.msavaliacredito.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msstudies.msavaliacredito.domain.model.DadosCliente;

@FeignClient(value = "msclientes", path = "/clientes")
public interface ClientesResourceClient {
	
    @GetMapping(params = "cpf")
    ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);

}
