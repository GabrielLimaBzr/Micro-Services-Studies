package com.msstudies.msavaliacredito.infra.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msstudies.msavaliacredito.domain.model.Cartao;
import com.msstudies.msavaliacredito.domain.model.CartaoCliente;



@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartaoResourceCliente {
	
    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam("cpf") String cpf);

    @GetMapping(params = "renda")
    ResponseEntity<List<Cartao>> getCartaoRenda(@RequestParam("renda") Long renda);

}
