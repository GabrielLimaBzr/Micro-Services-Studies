package com.msstudies.msclientes.applications;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.msstudies.msclientes.applications.dtos.ClienteSaveDTO;
import com.msstudies.msclientes.applications.services.ClienteService;
import com.msstudies.msclientes.domain.Cliente;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClientesResources {
	
	private final ModelMapper model; 
	
	private final ClienteService service;
	
	@GetMapping
	public String  status() {
		return  "ok";
	}
	
	@PostMapping
	public ResponseEntity<Cliente> saveCliente(@RequestBody ClienteSaveDTO request){
		var cliente = model.map(request, Cliente.class);
		service.save(cliente);
		URI headerLocation = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.query("cpf={cpf}")
				.buildAndExpand(cliente.getCpf())
				.toUri();
		return ResponseEntity.created(headerLocation).build();
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<ClienteSaveDTO> retornaCliente(@RequestParam("cpf") String cpf){
		var cliente =  service.getByCpf(cpf);
		if(cliente == null){
            return ResponseEntity.notFound().build();
        }
		return ResponseEntity.ok(model.map(cliente, ClienteSaveDTO.class));
	}
	
//	@ExceptionHandler(ClienteNotFoundException.class)
//    public ResponseEntity<String> handleObjetoNaoEncontradoException(ClienteNotFoundException e) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//    }

}
