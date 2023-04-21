package com.msstudies.msavaliacredito.application.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.msstudies.msavaliacredito.application.exceptions.DadosClienteNotFoundException;
import com.msstudies.msavaliacredito.application.exceptions.ErroComunicacaoServidorException;
import com.msstudies.msavaliacredito.application.exceptions.ErroSolicitacaoCartaoException;
import com.msstudies.msavaliacredito.domain.model.Cartao;
import com.msstudies.msavaliacredito.domain.model.CartaoAprovado;
import com.msstudies.msavaliacredito.domain.model.DadosCliente;
import com.msstudies.msavaliacredito.domain.model.DadosSolicitacaoCartao;
import com.msstudies.msavaliacredito.domain.model.ProtocoloSolicitacaoCartao;
import com.msstudies.msavaliacredito.domain.model.RetornaAvaliacaoCliente;
import com.msstudies.msavaliacredito.domain.model.SituacaoCliente;
import com.msstudies.msavaliacredito.infra.clients.CartaoResourceCliente;
import com.msstudies.msavaliacredito.infra.clients.ClientesResourceClient;
import com.msstudies.msavaliacredito.infra.mqueue.EmissaoCartaoPublisher;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliaCreditoService {
	
	private final ClientesResourceClient clientesClient;
	private final CartaoResourceCliente cartaoClient;
	private final EmissaoCartaoPublisher emissaoCartaoPublisher;
	
	
	
	public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoServidorException{
		 
		try {
			
			var objCliente = clientesClient.dadosCliente(cpf);
			var objCartao = cartaoClient.getCartoesByCliente(cpf);
			return SituacaoCliente
					.builder()
					.cliente(objCliente.getBody())
					.cartoes(objCartao.getBody())
					.build();
			
		} catch (FeignException.FeignClientException ex) {
			var status = ex.status();
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			
			throw new ErroComunicacaoServidorException(ex.contentUTF8(), status);
		}
	}
	
	public RetornaAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException, ErroComunicacaoServidorException{
		try {
			
			var objCliente = clientesClient.dadosCliente(cpf);
			var objCartao = cartaoClient.getCartaoRenda(renda);
			
			List<Cartao> cartoes = objCartao.getBody();
			DadosCliente  cliente = objCliente.getBody();
			
			var listaCartoesAprovados = cartoes.stream().map(c ->{
				
				BigDecimal limiteBasico = c.getLimiteBasico();
				var dataNasc = cliente.getDataNascimento();
				var hoje = LocalDate.now();
				BigDecimal idade =  new BigDecimal(Period.between(dataNasc, hoje).getYears());
				var fator = idade.divide(BigDecimal.valueOf(10));
				var limiteAprovado = fator.multiply(limiteBasico);
				
				CartaoAprovado aprovado = new CartaoAprovado();
				aprovado.setCartao(c.getNome());
				aprovado.setBandeira(c.getBandeira());
				aprovado.setLimiteAprovado(limiteAprovado);
				
				return aprovado;
			}).collect(Collectors.toList());
			
			return new RetornaAvaliacaoCliente(listaCartoesAprovados);
			
		} catch (FeignException.FeignClientException ex) {
			var status = ex.status();
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			
			throw new ErroComunicacaoServidorException(ex.contentUTF8(), status);
		}
	}
	
	public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoCartao dados) {
		try {
			emissaoCartaoPublisher.solicitarCartao(dados);
			var protocolo = UUID.randomUUID().toString();
			return new ProtocoloSolicitacaoCartao(protocolo);
		} catch (Exception e) {
			throw new ErroSolicitacaoCartaoException(e.getMessage());
		}
	}

}
