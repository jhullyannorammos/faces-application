package br.com.application.controller.tools;

import br.com.application.model.Pedido;

public class PedidoAlteradoEvent {

	private Pedido pedido;
	
	public PedidoAlteradoEvent(Pedido pedido) {
		this.pedido = pedido;
	}

	public Pedido getPedido() {
		return pedido;
	}
	
}
