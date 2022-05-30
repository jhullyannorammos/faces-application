package br.com.application.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.application.model.ItemPedido;
import br.com.application.model.Pedido;
import br.com.application.repository.PedidoRepository;
import br.com.application.util.jpa.Transactional;

public class EstoqueService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidos;
	
	@Transactional
	public void baixarItensEstoque(Pedido pedido) throws NegocioException {
		pedido = this.pedidos.porId(pedido.getId());
		
		for (ItemPedido item : pedido.getItens()) {
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	}

	public void retornarItensEstoque(Pedido pedido) {
		pedido = this.pedidos.porId(pedido.getId());
		
		for (ItemPedido item : pedido.getItens()) {
			item.getProduto().adicionarEstoque(item.getQuantidade());
		}
	}
	
}
