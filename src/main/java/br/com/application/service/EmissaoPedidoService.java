package br.com.application.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.application.model.Pedido;
import br.com.application.model.StatusPedido;
import br.com.application.repository.PedidoRepository;
import br.com.application.util.jpa.Transactional;

@SuppressWarnings("serial")
public class EmissaoPedidoService implements Serializable {

	@Inject private CadastroPedidoService cadastroPedidoService;
	@Inject private EstoqueService estoqueService;
	@Inject private PedidoRepository pedidos;
	
	@Transactional
	public Pedido emitir(Pedido pedido) throws NegocioException {
		pedido = this.cadastroPedidoService.salvar(pedido);
		
		if (pedido.isNaoEmissivel()) {
			throw new NegocioException("Pedido não pode ser emitido com status " + pedido.getStatus().getDescricao() + ".");
		}
		
		this.estoqueService.baixarItensEstoque(pedido);
		pedido.setStatus(StatusPedido.EMITIDO);
		pedido = this.pedidos.guardar(pedido);
		return pedido;
	}
	
}
