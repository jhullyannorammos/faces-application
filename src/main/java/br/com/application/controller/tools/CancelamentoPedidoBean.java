package br.com.application.controller.tools;

import java.io.Serializable;


import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.application.controller.PedidoEdicao;
import br.com.application.controller.tools.PedidoAlteradoEvent;
import br.com.application.model.Pedido;
import br.com.application.service.CancelamentoPedidoService;
import br.com.application.service.NegocioException;
import br.com.application.util.jsf.FacesUtil;

@SuppressWarnings("serial")
@Named
@RequestScoped
public class CancelamentoPedidoBean implements Serializable {

	@Inject private CancelamentoPedidoService cancelamentoPedidoService;
	@Inject private Event<PedidoAlteradoEvent> pedidoAlteradoEvent;
	@PedidoEdicao
	@Inject private Pedido pedido;
	
	public void cancelarPedido() {
		try {
			this.pedido = this.cancelamentoPedidoService.cancelar(this.pedido);
			this.pedidoAlteradoEvent.fire(new PedidoAlteradoEvent(this.pedido));
			
			FacesUtil.addInfoMessage("Pedido cancelado com sucesso!");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
}
