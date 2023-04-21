package com.msstudies.mscartoes.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msstudies.mscartoes.domain.Cartao;
import com.msstudies.mscartoes.domain.ClienteCartao;
import com.msstudies.mscartoes.domain.DadosSolicitacaoCartao;
import com.msstudies.mscartoes.infra.repositories.CartaoRepository;
import com.msstudies.mscartoes.infra.repositories.ClienteCartaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {
	
	private final CartaoRepository cartaoRepository;
	private final ClienteCartaoRepository clienteCartaoRepository;

	
	@RabbitListener(queues = "${mq.queues.emissao-cartoes}")
	public void receberSolicitacao(@Payload String payload) {
		
		try {
			var mapper =  new ObjectMapper();
			DadosSolicitacaoCartao dados = mapper.readValue(payload, DadosSolicitacaoCartao.class);
			Cartao cartao =  cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
			ClienteCartao clienteCartao  = new ClienteCartao(); 
			clienteCartao.setCartao(cartao);
			clienteCartao.setCpf(dados.getCpf());
			clienteCartao.setLimiteLiberado(dados.getLimiteLiberado());
			
			clienteCartaoRepository.save(clienteCartao);
			
		} catch (Exception e) {
			log.error("Erro ao receber solicitação de cartão: {}", e);
		} 
	}
}
