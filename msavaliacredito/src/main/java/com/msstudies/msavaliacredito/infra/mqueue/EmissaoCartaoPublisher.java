package com.msstudies.msavaliacredito.infra.mqueue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msstudies.msavaliacredito.domain.model.DadosSolicitacaoCartao;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoPublisher {
	
	private final RabbitTemplate rabbitTemplate;
	private final Queue queueEmissaoCartoes;
	
	public void solicitarCartao(DadosSolicitacaoCartao dados) throws JsonProcessingException{
		var json = convertJson(dados);
		rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), json);
	}
	
	private String convertJson(DadosSolicitacaoCartao convert)throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(convert);
	}
	

}
